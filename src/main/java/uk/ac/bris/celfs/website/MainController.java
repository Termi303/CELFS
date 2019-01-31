package uk.ac.bris.celfs.website;

import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.coursework.CourseworkEntryService;
import uk.ac.bris.celfs.services.TeacherService;
import uk.ac.bris.celfs.services.StudentService;
import uk.ac.bris.celfs.services.MicroResearchReportService;
import uk.ac.bris.celfs.database.MicroResearchReport;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.bris.celfs.services.BandService;


@Controller
public class MainController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseworkEntryService courseworkEntryService;

    @Autowired
    private BandService bandService;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        teacherService.init();
        studentService.init();
    }
    
    @GetMapping("/nav")
    public String nav() {
        return "nav";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/mrr")
    public String mrr(@ModelAttribute("mrrRaw") MrrCommand command, Model model) {
        if(command == null){
            model.addAttribute( "command", new MrrCommand());
        } else {
            model.addAttribute("command", command);
        }
        
        return "mrr";
    }

    @PostMapping("/mrr")
    public String submitMrr(@ModelAttribute("command") MrrCommand command, BindingResult binding,   
			Model model, RedirectAttributes ra ) {
        if (binding.hasErrors()) {
            return "/error";
        }
        
        ra.addFlashAttribute("command", command);

        return "redirect:/reviewmrr";
    }
    
    
    @GetMapping("/reviewmrr")
    public String reviewmrr(HttpServletRequest request, @ModelAttribute("command") MrrCommand command,
			Model model) {
        
        int[][] rs;
        rs = CalculateMarks.sepCat(command);
        
        model.addAttribute("mrrRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getAvg(CalculateMarks.getBandAvg(rs[0]),CalculateMarks.getBandAvg(rs[1]),
                CalculateMarks.getBandAvg(rs[2])));
        model.addAttribute("tfcGrade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[0])));
        model.addAttribute("lsGrade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[1])));
        model.addAttribute("toGrade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[2])));
        request.getSession().setAttribute("mrr", command);
        
        CalculateMarks calc = new CalculateMarks();
        
        model.addAttribute("Calc", calc);
        
        return "reviewmrr";
    }    
    
    @RequestMapping(value="/reviewmrr",params="editButton",method=RequestMethod.POST)
    public String editMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        
        MrrCommand m = (MrrCommand) request.getSession().getAttribute("mrr");
        //System.out.println(m);
        
        ra.addFlashAttribute("mrrRaw", m);
        
        return "redirect:/mrr";
    }
    
    @RequestMapping(value="/reviewmrr",params="submitButton",method=RequestMethod.POST)
    public String submitMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        
        MrrCommand m = (MrrCommand) request.getSession().getAttribute("mrr");
        
        int[][] rs;
        rs = CalculateMarks.sepCat(m);

        Integer taskFullfilment = CalculateMarks.getBandAvg(rs[0]);
        Integer languageUse = CalculateMarks.getBandAvg(rs[1]);
        Integer organisation = CalculateMarks.getBandAvg(rs[2]);
        Integer overallScore = CalculateMarks.getAvg(taskFullfilment, languageUse, organisation);
        
        ra.addFlashAttribute("id", m.studentID);
        ra.addFlashAttribute("grade", overallScore);
        
        //Insert student into database
        Student student = new Student(m.studentID, "SEAT1", "MICRO_RESEARCH");
        studentService.add(student);

        System.out.println("Student added: " + student.toString());

        //Get any teacher from database
        //Teacher teacher = teacherService.getAny();

        //System.out.println("Teacher merged: " + teacher.toString());

        //Insert microResearchReport
        CourseworkEntry report = new CourseworkEntry(student/*, teacher*/, taskFullfilment,
                languageUse, organisation, overallScore);
        report.setComment(m.overallComment);

        System.out.println("Report created: " + report.toString());

        courseworkEntryService.add(report);

        System.out.println("Report inserted into database");
        
        return "redirect:/resultPage";
    }
    
    

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/resultPage")
    public String resultPage(HttpServletRequest request, @ModelAttribute("id") String studentID, @ModelAttribute("grade") String grade,
            Model model) {
        
        model.addAttribute("id", studentID);
        model.addAttribute("grade", grade);
        
        return "resultPage";
    }
    
    @GetMapping("/showMarks")
    public String showMarks(Model model) {
        model.addAttribute("command", new ShowMarksCommand());
        model.addAttribute("results", courseworkEntryService.getAll());
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        
        return "showMarks";
    }
    
    @PostMapping("/showMarks")
    public String searchMarks(@ModelAttribute("command") ShowMarksCommand command, BindingResult binding,   
			Model model, RedirectAttributes ra ) {
        
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
