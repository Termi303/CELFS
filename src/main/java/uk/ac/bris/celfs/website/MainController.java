package uk.ac.bris.celfs.website;

import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.factory.DataFactory;
import uk.ac.bris.celfs.services.CourseworkEntryService;
import uk.ac.bris.celfs.services.TeacherService;
import uk.ac.bris.celfs.services.StudentService;
import uk.ac.bris.celfs.database.Student;
import java.util.ArrayList;
import java.util.List;
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
import uk.ac.bris.celfs.services.TablesService;


@Controller
public class MainController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseworkEntryService courseworkEntryService;

    @Autowired
    private TablesService tablesService;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        teacherService.init();
        studentService.init();
        DataFactory.buildData(tablesService);
    }


    private void addWorks(Model model){
        ArrayList<String> works = new ArrayList<>();
        works.add("Micro Research Report");
        works.add("Short Answer Question");
        model.addAttribute("works", works);
    }
    
    @GetMapping("/nav")
    public String nav() {
        return "nav";
    }

    @GetMapping("/")
    public String index(Model model) {
        addWorks(model);
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model) {
        addWorks(model);
        return "error";
    }

    private static void setTestModel(CourseworkCommand com, Model model){
        String[] categ = DataFactory.categoryNames[0];
        String[] bands = DataFactory.bandNames;
        String[][][] crit = DataFactory.criteriaAndBands;


        model.addAttribute("categ", categ);
        model.addAttribute("bands", bands);
        model.addAttribute("crit", crit);

        Keywords k = new Keywords();

        model.addAttribute("keywords", k);
        
        CourseworkCommand command = new CourseworkCommand();
        
        for (int i = 0; i < categ.length; i++){
            command.addCat();
            for(int j = 1; j <= crit[i].length; j++){
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
    public String coursework(@ModelAttribute("courseworkRaw") CourseworkCommand command, 
            @RequestParam("id") String id, Model model, RedirectAttributes ra) {
        addWorks(model);
        
        model.addAttribute("id", id);

        setTestModel(command, model);
        
        ra.addFlashAttribute("id", id);

        return "coursework";
    }

    @PostMapping("/coursework")
    public String submitMrr(@ModelAttribute("command") CourseworkCommand command, BindingResult binding,
			@ModelAttribute("id") String id, Model model, RedirectAttributes ra ) {
        addWorks(model);
        if (binding.hasErrors()) {
            return "/error";
        }

        ra.addFlashAttribute("command", command);
        ra.addFlashAttribute("id", id);

        return "redirect:/reviewcoursework";
    }


    @GetMapping("/reviewcoursework")
    public String reviewcoursework(HttpServletRequest request, @ModelAttribute("command") CourseworkCommand command,
			Model model) {
        addWorks(model);

        setTestModel(command, model);

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
        addWorks(model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        ra.addFlashAttribute("courseworkRaw", m);

        String result = "redirect:/coursework?id=" + id;
        
        return result;
    }

    @RequestMapping(value="/reviewcoursework",params="submitButton",method=RequestMethod.POST)
    public String submitMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addWorks(model);

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
    public String login(Model model) {
        addWorks(model);
        model.addAttribute( "command", new LoginCommand());
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        addWorks(model);
        return "admin";
    }

    @GetMapping("/resultPage")
    public String resultPage(HttpServletRequest request, @ModelAttribute("id") String studentID, @ModelAttribute("grade") String grade,
            Model model) {
        addWorks(model);

        model.addAttribute("id", studentID);
        model.addAttribute("grade", grade);

        return "resultPage";
    }

    @GetMapping("/showMarks")
    public String showMarks(Model model) {
        addWorks(model);
        model.addAttribute("command", new ShowMarksCommand());
        model.addAttribute("results", courseworkEntryService.getAll());
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());

        return "showMarks";
    }

    @PostMapping("/showMarks")
    public String searchMarks(@ModelAttribute("command") ShowMarksCommand command, BindingResult binding,
			Model model, RedirectAttributes ra ) {
        addWorks(model);

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
}
