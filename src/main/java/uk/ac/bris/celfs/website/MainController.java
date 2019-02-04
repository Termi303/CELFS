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

    private static void setTestModel(MrrCommand com, Model model){
        String[] categ = {"Task Fulfilment and Content", "Language and Style", "Text Organisation"};
        String[] bands = {"Criterion", "Exceptional", "Very Good", "Good", "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
        String[][][] crit = {{{"Response",
            "Rigorous, lucid, creative & original response",
            "Complete, relevant, fairly sophisticated response to task with noticeable quality of ideas",
            "No major omissions and mostly relevant response to task but may lack sophistication",
            "No major omissions with some successful attempts to communicate main ideas but some repetition, irrelevance",
            "Minimal response to task, with only one major omission (missing one IMRD section, missing source, no reference list, no visual summary)",
            "Inadequate response to task which misses more than one major element of task",
            "Fails to address the general scope of the task",
            "No attempt at task; evidence of cheating"},
            {"Magic", "responses responding responded Responded rESPONSE rEsPOndeD", "two", "three", "four", "five", "six", "seven", "eight"},
        {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
        {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
        {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
        {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}},
            {{"Control",
            "Full control of a wide variety of sentence structures",
            "Very good control of a variety of sentence structures though possibly some inappropriacies",
            "Control of a variety of sentence structures",
            "Control of simple sentence structures with some successful attempts at variety",
            "Control of basic sentence structure but attempts at variety more unsuccessful than not",
            "Basic sentence structure is sound but no attempt at variety",
            "Little evidence of basic sentence structure",
            "Incomprehensible due to lack of control of structure or word choice/form"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}},
            {{"Sentence Structure",
            "Writer responsibility is fully met and thesis is outstanding for originality / creativity / elegance",
            "Non-specialist audience very well prepared for topic, task and argument: concise, sophisticated thesis with well-placed map explaining order of key points, which is then followed",
            "Non-specialist audience well prepared for topic and task: rationale for choice, key definitions, thesis, purpose and map",
            "Thesis easily located but lacks clarity on the relationship between texts and/or how this will be explained (no map) and/or purpose given too late so initial confusion caused",
            "Research question shows topic focus but thesis is not easily located and it is unclear how the question will be answered (no purpose)",
            "Topic has not been narrowed to research question",
            "No attempt to introduce topic or draw conclusions",
            "No awareness of academic conventions is evidenced"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}}};


        model.addAttribute("categ", categ);
        model.addAttribute("bands", bands);
        model.addAttribute("crit", crit);

        Keywords k = new Keywords();

        model.addAttribute("keywords", k);
        
        MrrCommand command = new MrrCommand();
        
        for (int i = 0; i < categ.length; i++){
            command.addCat();
            for(int j = 1; j <= crit[i].length; j++){
                command.addCrit(i,"", "");
            }
        }
        
        System.out.println(command);
        
        if(com == null){
            model.addAttribute( "command", command);
        } else {
            model.addAttribute("command", com);
        }
        
    }

    @GetMapping("/mrr")
    public String mrr(@ModelAttribute("mrrRaw") MrrCommand command, Model model) {

        setTestModel(command, model);

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

        setTestModel(command, model);

        int[][] rs;
        rs = CalculateMarks.sepCat(command);

        model.addAttribute("mrrRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getAvg(CalculateMarks.getBandAvg(rs[0]),CalculateMarks.getBandAvg(rs[1]),
                CalculateMarks.getBandAvg(rs[2])));
        model.addAttribute("v_1Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[0])));
        model.addAttribute("v_2Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[1])));
        model.addAttribute("v_3Grade", CalculateMarks.applyMark(CalculateMarks.getBandAvg(rs[2])));
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
        Float overallScore = CalculateMarks.getAvg(taskFullfilment, languageUse, organisation);

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
                languageUse, organisation, overallScore.intValue());
        report.setComment(m.overallComment);

        System.out.println("Report created: " + report.toString());

        courseworkEntryService.add(report);

        System.out.println("Report inserted into database");

        return "redirect:/resultPage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute( "command", new LoginCommand());
        return "login";
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
