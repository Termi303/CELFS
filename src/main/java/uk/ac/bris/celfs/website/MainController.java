package uk.ac.bris.celfs.website;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.factory.DataFactory;
import uk.ac.bris.celfs.services.*;
import uk.ac.bris.celfs.database.Student;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private List<String> works;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        studentService.init();
        userService.init();
        Keywords.init();
        DataFactory.buildData(tablesService);

        works = tablesService.getAllCourseworksNames();
    }

    private User addGeneralStuff (HttpServletRequest request, Model model) {
        model.addAttribute("works", works);
        Object user = request.getSession().getAttribute("user");
        
        if (user == null){
            //System.out.println("User null");
            model.addAttribute("user", new User("", "", UserType.NULL));
        } else {
            //System.out.println("User not null");
            model.addAttribute("user", (User) user);
        }
        //System.out.println(new User("", "", UserType.NULL));
        
        return (User) user;
    }
    
    private User addReGeneralStuff (HttpServletRequest request, RedirectAttributes ra){
        ra.addFlashAttribute("works", works);
        Object user = request.getSession().getAttribute("user");
        
        if (user == null){
            //System.out.println("User null");
            ra.addFlashAttribute("user", new User("", "", UserType.NULL));
        } else {
            //System.out.println("User not null");
            ra.addFlashAttribute("user", (User) user);
        }
        //System.out.println(new User("", "", UserType.NULL));
        
        return (User) user;
    }

    @GetMapping("/nav")
    public String nav() {
        return "nav";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        addGeneralStuff(request, model);
        return "index";
    }
    
    @PostMapping("/")
    public String indexPost(HttpServletRequest request, Model model) {
        request.getSession().invalidate();
        addGeneralStuff(request, model);
        return "index";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        addGeneralStuff(request, model);
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

        Keywords k = new Keywords();

        model.addAttribute("keywords", k);

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
        User u = addGeneralStuff(request, model);
        if (u == null){
          return "redirect:/login";
        } else {       
            Object courseworkName;
            if (command != null){
                request.getSession().setAttribute("type", id);
                courseworkName = id;
            } else {
                courseworkName = request.getSession().getAttribute("type");
            }
            model.addAttribute("id", courseworkName);
            setTestModel(command, model, (String) courseworkName);
            return "coursework";
        }
    }

    @PostMapping("/coursework")
    public String submitMrr(@ModelAttribute("command") CourseworkCommand command, BindingResult binding,
			HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addGeneralStuff(request, model);
        if (binding.hasErrors()) {
            return "/error";
        }

        ra.addFlashAttribute("command", command);

        return "redirect:/reviewcoursework";
    }


    @GetMapping("/reviewcoursework")
    public String reviewcoursework(HttpServletRequest request, @ModelAttribute("command") CourseworkCommand command,
			Model model) {
        addGeneralStuff(request, model);
        model.addAttribute("id", request.getSession().getAttribute("type"));
        

        setTestModel(command, model, (String) request.getSession().getAttribute("type"));

        int[][] rs;
        rs = CalculateMarks.sepCat(command);

        model.addAttribute("courseworkRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getAvg(CalculateMarks.getBandAvg(rs[0]),CalculateMarks.getBandAvg(rs[1]),
                CalculateMarks.getBandAvg(rs[2])));
        model.addAttribute("v_1Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[0])));
        model.addAttribute("v_2Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[1])));
        model.addAttribute("v_3Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[2])));
        request.getSession().setAttribute("coursework", command);

        CalculateMarks calc = new CalculateMarks();

        model.addAttribute("Calc", calc);

        return "reviewcoursework";
    }

    @RequestMapping(value="/reviewcoursework",params="editButton",method=RequestMethod.POST)
    public String editMrr(HttpServletRequest request, @ModelAttribute("id") String id, Model model, RedirectAttributes ra ) {
        addGeneralStuff(request, model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        ra.addFlashAttribute("courseworkRaw", m);
        ra.addAttribute("id", request.getSession().getAttribute("type"));
        
        return "redirect:/coursework";
    }

    @RequestMapping(value="/reviewcoursework",params="submitButton",method=RequestMethod.POST)
    public String submitMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addGeneralStuff(request, model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        int[][] rs;
        rs = CalculateMarks.sepCat(m);

        Integer taskFullfilment = CalculateMarks.getBandAvg(rs[0]);
        Integer languageUse = CalculateMarks.getBandAvg(rs[1]);
        Integer organisation = CalculateMarks.getBandAvg(rs[2]);
        Float overallScore = CalculateMarks.getAvg(taskFullfilment, languageUse, organisation);

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
        CourseworkEntry report = new CourseworkEntry(student/*, teacher*/, taskFullfilment,
                languageUse, organisation, overallScore);
        report.setComment(m.overallComment);

        System.out.println("Report created: " + report.toString());

        courseworkEntryService.add(report);

        System.out.println("Report inserted into database");

        return "redirect:/resultPage";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        addGeneralStuff(request, model);
        model.addAttribute( "command", new LoginCommand());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, Model model,
      @ModelAttribute("command") LoginCommand command, RedirectAttributes ra) {
        
      User user = userService.getUser(command.email, command.password);
      
      if (user != null) {
        //System.out.println("log in success, username=" + command.email);
        request.getSession().setAttribute("user", user);
        addReGeneralStuff(request, ra);
        return "redirect:/";
      } else {
        //System.out.println("log in :(");
        addReGeneralStuff(request, ra);
        return "redirect:/login";
      }
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Model model) {
      User u = addGeneralStuff(request, model);
      if (u == null){
        return "redirect:/login";
      } else {
        return "admin";
      }
    }
    
    @GetMapping("/editStudents")
    public String editStudents(HttpServletRequest request, Model model) {
      User u = addGeneralStuff(request, model);
      if (u == null){
        return "redirect:/login";
      } else {
          
          List<Student> students = studentService.getAll();
          model.addAttribute("students", students);
          model.addAttribute("command", new StudentCommand());
          
          
        return "editStudents";
      }
      
    }
    
    @PostMapping("/editStudents")
    public String editStudentsPost(HttpServletRequest request, Model model,
            @ModelAttribute("command") StudentCommand command) {
      User u = addGeneralStuff(request, model);
      if (u == null){
        return "redirect:/login";
      } else {
          
        studentService.add(command.id, command.seat, command.s_class);
          
          
        return "redirect:/editStudents";
      }
      
    }

    @GetMapping("/resultPage")
    public String resultPage(HttpServletRequest request, @ModelAttribute("id") String studentID, @ModelAttribute("grade") String grade,
            Model model) {
        User u = addGeneralStuff(request, model);
        if (u == null){
          return "redirect:/login";
        } else {
        model.addAttribute("id", studentID);
        model.addAttribute("grade", grade);

        return "resultPage";
      }
    }

    @GetMapping("/showMarks")
    public String showMarks(HttpServletRequest request, Model model) {
        User u = addGeneralStuff(request, model);
        if (u == null){
          return "redirect:/login";
        } else {
        model.addAttribute("command", new ShowMarksCommand());
        model.addAttribute("results", courseworkEntryService.getAll());
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        return "showMarks";
      }
    }

    @PostMapping("/showMarks")
    public String searchMarks(@ModelAttribute("command") ShowMarksCommand command, BindingResult binding,
			Model model, RedirectAttributes ra, HttpServletRequest request) {
        addGeneralStuff(request, model);

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
        User u = addGeneralStuff(request, model);
        if (u == null){
          return "redirect:/login";
        } else {
            
          UpdateMarksCommand myCommand = new UpdateMarksCommand();
        
          List<CourseworkEntry> results = courseworkEntryService.getAll();
          for(CourseworkEntry entry : results)
          {
              myCommand.updatedMarks.put(entry.getId(), "");
          }
            
        model.addAttribute("command", myCommand);
        model.addAttribute("updatedMark", myCommand.updatedMarks);
        model.addAttribute("results", results);
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        return "adminShowMarks";
      }
    }

    @PostMapping("/adminShowMarks")
    public String adminUpdateMarks(@ModelAttribute("command") UpdateMarksCommand command, BindingResult binding,
			Model model, RedirectAttributes ra, HttpServletRequest request) {
        addGeneralStuff(request, model);

        if (binding.hasErrors()) {
            System.out.println("binding had errors\n");
            return "/error";
        }
        
        if(!command.updatedMarks.isEmpty())
        {
            System.out.println("We should update:");
            
            for (Iterator it = command.updatedMarks.entrySet().iterator(); it.hasNext();) {
                Map.Entry m = (Map.Entry) it.next();
                System.out.println(m.getKey()+" "+m.getValue());  
            }
            System.out.println("End of what should be updated");
        }
        
        else 
        {
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
        }
        

        return "adminShowMarks";
    }
}
