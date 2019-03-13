package uk.ac.bris.celfs.website;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.factory.DataFactory;
import uk.ac.bris.celfs.services.*;
import uk.ac.bris.celfs.database.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.bris.celfs.database.User;
import uk.ac.bris.celfs.database.UserType;

@Controller
public class MainController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseworkEntryService courseworkEntryService;

    @Autowired
    private TablesService tablesService;

    @Autowired
    private UserService userService;

    @Autowired
    private KeywordService keywordService;

    private List<String> works;

    private Keywords keywords;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        studentService.init();
        userService.init();
        keywords = new Keywords(keywordService);
        keywords.init();
        DataFactory.buildData(tablesService);

        works = tablesService.getAllCourseworksNames();
    }

    private UserType getUserType(User user){
        if(user == null) return UserType.NULL;
        else return user.getUserType();
    }
    
    private User addAttributes (HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserFromUsername(authentication.getName());
        
        model.addAttribute("works", works);
        model.addAttribute("type", getUserType(user));
        model.addAttribute("name", authentication.getName());
        
        return user;
    }

    @GetMapping("/nav")
    public String nav() {
        return "nav";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        return "index";
    }

    @PostMapping("/")
    public String indexPost(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        request.getSession().invalidate();
        return "redirect:/index";
    }
    
    @GetMapping("/index")
    public String indexI(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        return "index";
    }

    @PostMapping("/index")
    public String indexIPost(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        request.getSession().invalidate();
        return "redirect:/index";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        return "error";
    }

    private void setTestModel(CourseworkCommand com, Model model, String courseworkName){
        Coursework coursework = tablesService.getCourseworkByName(courseworkName);
        String[] categ = tablesService.getCategoriesNames(coursework.getId());
        String[] bands = tablesService.getAllBandsNames();
        List<List<List<String>>> crit = tablesService.getTable(coursework.getId());


        model.addAttribute("categ", categ);
        model.addAttribute("bands", bands);
        model.addAttribute("crit", crit);

        model.addAttribute("keywords", keywords);

        CourseworkCommand command = new CourseworkCommand();

        for (int i = 0; i < categ.length; i++){
            command.addCat();
            for(int j = 1; j <= crit.get(i).size(); j++){
                command.addCrit(i,"", "");
            }
        }

        if(com == null){
            model.addAttribute( "command", command);
        } else {
            model.addAttribute("command", com);
        }

    }
    
    @GetMapping("/coursework")
    public String coursework(HttpServletRequest request, @ModelAttribute("courseworkRaw") CourseworkCommand command,
            @RequestParam("id") String id, Model model, RedirectAttributes ra) {
        User u = addAttributes(request, model);
            Object courseworkName;
            if (command != null){
                request.getSession().setAttribute("type", id);
                courseworkName = id;
            } else {
                courseworkName = request.getSession().getAttribute("type");
            }
            model.addAttribute("id", courseworkName);
            setTestModel(command, model, (String) courseworkName);
            
            UserType type = getUserType(u);
            
            if (type != UserType.TEACHER) return "redirect:/index";
            else return "coursework";
    }

    @PostMapping("/coursework")
    public String submitMrr(@ModelAttribute("command") CourseworkCommand command, BindingResult binding,
			HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addAttributes(request, model);
        if (binding.hasErrors()) {
            return "/error";
        }

        ra.addFlashAttribute("command", command);

        return "redirect:/reviewcoursework";
    }


    @GetMapping("/reviewcoursework")
    public String reviewcoursework(HttpServletRequest request, @ModelAttribute("command") CourseworkCommand command,
			Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("id", request.getSession().getAttribute("type"));


        setTestModel(command, model, (String) request.getSession().getAttribute("type"));

        int[][] rs;
        rs = CalculateMarks.separateCategories(command);

        List<Float> weights = new ArrayList<>();
        weights.add(0.4f);
        weights.add(0.2f);
        weights.add(0.4f);

        model.addAttribute("courseworkRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getOverallScore(buildCategoryAverage(rs), weights));
        model.addAttribute("v_1Grade", CalculateMarks.applyMark(CalculateMarks.getBandAverage(rs[0])));
        model.addAttribute("v_2Grade", CalculateMarks.applyMark(CalculateMarks.getBandAverage(rs[1])));
        model.addAttribute("v_3Grade", CalculateMarks.applyMark(CalculateMarks.getBandAverage(rs[2])));
        request.getSession().setAttribute("coursework", command);

        CalculateMarks calc = new CalculateMarks();

        model.addAttribute("Calc", calc);

        UserType type = getUserType(u);
        
        if (type != UserType.TEACHER) return "redirect:/index";
        else return "reviewcoursework";
    }

    @RequestMapping(value="/reviewcoursework",params="editButton",method=RequestMethod.POST)
    public String editMrr(HttpServletRequest request, @ModelAttribute("id") String id, Model model, RedirectAttributes ra ) {
        addAttributes(request, model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        ra.addFlashAttribute("courseworkRaw", m);
        ra.addAttribute("id", request.getSession().getAttribute("type"));

        return "redirect:/coursework";
    }

    private List<Integer> buildCategoryAverage(int[][] rs) {
        List<Integer> categoryAverage = new ArrayList<>();
        for(int i = 0; i < rs.length; i++) {
            categoryAverage.add(CalculateMarks.getBandAverage(rs[i]));
        }
        return categoryAverage;
    }

    @RequestMapping(value="/reviewcoursework",params="submitButton",method=RequestMethod.POST)
    public String submitMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addAttributes(request, model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        int[][] rs;
        rs = CalculateMarks.separateCategories(m);

        List<Integer> categoryAverage = buildCategoryAverage(rs);

        List<Float> weights = new ArrayList<>();
        weights.add(0.4f);
        weights.add(0.2f);
        weights.add(0.4f);
        Float overallScore = CalculateMarks.getOverallScore(categoryAverage, weights);

        ra.addFlashAttribute("id", m.studentID);
        ra.addFlashAttribute("grade", overallScore);

        //Insert student into database
        Student student = new Student(m.studentID, "SEAT1", "MICRO_RESEARCH");
        studentService.add(student);

        System.out.println("Student added: " + student.toString());

        //Get any teacher from database
        //User teacher = teacherService.getAny();

        //System.out.println("User merged: " + teacher.toString());

        //Insert microResearchReport
        CourseworkEntry report = new CourseworkEntry(student, categoryAverage, overallScore);
        report.setComment(m.overallComment);

        System.out.println("Report created: " + report.toString());

        courseworkEntryService.add(report);

        System.out.println("Report inserted into database");

        return "redirect:/resultPage";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        model.addAttribute( "command", new LoginCommand());
        return "login";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Model model) {
      User u = addAttributes(request, model);
      
      UserType type = getUserType(u);
        
      if (type != UserType.ADMIN) return "redirect:/index";
      else return "admin";
    }

    @GetMapping("/editStudents")
    public String editStudents(HttpServletRequest request, Model model) {
      User u = addAttributes(request, model);
      List<Student> students = studentService.getAll();
      model.addAttribute("students", students);
      model.addAttribute("command", new StudentCommand());


      UserType type = getUserType(u);
        
      if (type != UserType.ADMIN) return "redirect:/index";
      else return "editStudents";

    }

    @PostMapping("/editStudents")
    public String editStudentsPost(HttpServletRequest request, Model model,
            @ModelAttribute("command") StudentCommand command) {
      User u = addAttributes(request, model);
        studentService.add(command.id, command.seat, command.s_class);
        return "redirect:/editStudents";

    }

    @GetMapping("/resultPage")
    public String resultPage(HttpServletRequest request, @ModelAttribute("id") String studentID, @ModelAttribute("grade") String grade,
            Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("id", studentID);
        model.addAttribute("grade", grade);

        return "resultPage";
    }

    @GetMapping("/showMarks")
    public String showMarks(HttpServletRequest request, Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("command", new ShowMarksCommand());
        model.addAttribute("results", courseworkEntryService.getAll());
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        
              UserType type = getUserType(u);
        
      if (type != UserType.TEACHER) return "redirect:/index";
      else return "showMarks";
    }

    @PostMapping("/showMarks")
    public String searchMarks(@ModelAttribute("command") ShowMarksCommand command, BindingResult binding,
			Model model, RedirectAttributes ra, HttpServletRequest request) {
        addAttributes(request, model);

        if (binding.hasErrors()) {
            System.out.println("binding had errors\n");
            return "/error";
        }

        if("".equals(command.search)){
            model.addAttribute("command", new ShowMarksCommand());
            model.addAttribute("results", courseworkEntryService.getAll());
        } else {
            model.addAttribute("command", command);
            System.out.println(command.search);
            List<CourseworkEntry> reports = new ArrayList<>();
            if(courseworkEntryService.get(command.search) != null) {
                reports.add(courseworkEntryService.get(command.search));
            }
            model.addAttribute("results", reports);
        }

        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());

        return "showMarks";
    }


    @GetMapping("/adminShowMarks")
    public String adminShowMarks(HttpServletRequest request, Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("command", new ShowMarksCommand());
        model.addAttribute("results", courseworkEntryService.getAll());
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        
              UserType type = getUserType(u);
        
      if (type != UserType.ADMIN) return "redirect:/index";
      else return "adminShowMarks";
    }

    @PostMapping("/adminShowMarks")
    public String adminUpdateMarks(@ModelAttribute("command") UpdateMarksCommand command,
                                    @RequestParam String action,
                                    BindingResult binding,
                                    Model model,
                                    RedirectAttributes ra,
                                    HttpServletRequest request) {
        User u = addAttributes(request, model);

        if (binding.hasErrors()) {
            System.out.println("binding had errors\n");
            return "/error";
        }
        
        
        UpdateMarksCommand newCommand = new UpdateMarksCommand();
        List<CourseworkEntry> results = courseworkEntryService.getAll();
        for(CourseworkEntry entry : results)
        {
            newCommand.updatedMarks.add("");
        }

        if(!action.equals("filter"))
        {
            String identity = action.substring(8);
            ArrayList<String> newMarks = command.updatedMarks;
            
            int counter = 0;
            for(String mark : newMarks)
            {
                if(!mark.isEmpty()) counter++;
            }
            if(counter != 1)
            {
                System.out.println("Invalid input");
                model.addAttribute("command", newCommand);
                model.addAttribute("results", courseworkEntryService.getAll());
            }
            else
            {
                System.out.println("We should update:");
                System.out.println(identity);
                System.out.println("End of what should be updated");

                System.out.println("Update starting");

                for (int i=0; i<newMarks.size(); i++) {
                    if(!newMarks.get(i).isEmpty())
                    {
                        try { 
                            Float newMark = Float.parseFloat(newMarks.get(i));
                            if(!Objects.equals(newMark, courseworkEntryService.get(identity).getOverallScore()))
                            {
                                courseworkEntryService.updateMark(identity, newMark);
                            }
                        } 
                        catch (NumberFormatException e) { 
                            System.out.println("Error: Invalid Number");
                            System.out.println("Exception: " + e);
                        }
                    }
                }

                System.out.println("Update complete");

                model.addAttribute("command", newCommand);
                model.addAttribute("results", courseworkEntryService.getAll());
            }
        }

        else 
        {
            if("".equals(command.search)){

                model.addAttribute("command", newCommand);
                model.addAttribute("results", courseworkEntryService.getAll());
                System.out.println("Result size == " + courseworkEntryService.getAll().size());
                System.out.println(courseworkEntryService.getAll());
            }
            else 
            {
                model.addAttribute("command", newCommand);
                System.out.println(command.search);
                List<CourseworkEntry> reports = new ArrayList<>();
                if(courseworkEntryService.get(command.search) != null) {
                    reports.add(courseworkEntryService.get(command.search));
                }
                model.addAttribute("results", reports);

                System.out.println("Result size == " + reports.size());
                System.out.println(reports);
            }
        }
        System.out.println("-----------------------------------");
        return "adminShowMarks";

    }
    
    @GetMapping("/adminExportTable")
    public String adminExportTable(HttpServletRequest request, Model model) {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        if (type != UserType.ADMIN) return "redirect:/index";
        else return "adminExportTable";
    }

    @PostMapping("/adminExportTable")
    public ResponseEntity<InputStreamResource>  adminExportTable(@ModelAttribute("command") UpdateMarksCommand command,
                                    @RequestParam String action,
                                    BindingResult binding,
                                    Model model,
                                    ModelAndView mav,
                                    RedirectAttributes ra,
                                    HttpServletRequest request,
                                    HttpServletResponse response)  throws IOException  {
        User u = addAttributes(request, model);
        
        
        System.out.println("----------------- Exporting of Table ------------------");
        studentService.init();
        
        List<Student> students = (List<Student>) studentService.getAll();
        ByteArrayInputStream in = ExcelGenerator.studentsToExcel(students);
        // return IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
               headers.add("Content-Disposition", "attachment; filename=customers.xlsx");

        return ResponseEntity
                        .ok()
                        .headers(headers)
                        .body(new InputStreamResource(in));
    }
}
