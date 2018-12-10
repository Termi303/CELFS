package uk.ac.bris.celfs.website;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Controller
public class MainController {

    @Autowired
    private MarksRepository markRepository;
    
    // @Autowired
    // private MrrRawRepository mrrRawRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MicroResearchReportService microResearchReportService;

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
    
    @GetMapping("/marks")
    public String showMarkForm(Mark mark, Model model) {
        model.addAttribute("marks", markRepository.findAll());
        return "marks";
    }

    @PostMapping(value = "/mark")
    public String submitMark(@Valid Mark mark, BindingResult binding, RedirectAttributes attr) {
        if (binding.hasErrors()) {
            return "/error";
        }
        markRepository.save(mark);
        attr.addFlashAttribute("message", "Thank you for your quote.");
        return "redirect:/marks";
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
        Student student = new Student(htmlEscape(m.studentID), "SEAT1", "MICRO_RESEARCH");
        studentService.add(student);

        System.out.println("Student added: " + student.toString());

        //Get any teacher from database
        //Teacher teacher = teacherService.getAny();

        //System.out.println("Teacher merged: " + teacher.toString());

        //Insert microResearchReport
        MicroResearchReport report = new MicroResearchReport(student/*, teacher*/, taskFullfilment,
                languageUse, organisation, overallScore);
        report.setComment(htmlEscape(m.overallComment));

        System.out.println("Report created: " + report.toString());

        microResearchReportService.add(report);

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
    public String showMarks(HttpServletRequest request, Model model) {
        model.addAttribute("results", microResearchReportService.getAll());
        System.out.println("Result size == " + microResearchReportService.getAll().size());
        System.out.println(microResearchReportService.getAll());
        return "showMarks";
    }
}
