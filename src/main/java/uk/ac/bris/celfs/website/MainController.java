package uk.ac.bris.celfs.website;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import uk.ac.bris.celfs.coursework.CategoryEntry;
import uk.ac.bris.celfs.coursework.CellEntry;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.database.*;
import uk.ac.bris.celfs.factory.DataFactory;
import uk.ac.bris.celfs.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

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

    private List<Coursework> works;

    private Keywords keywords;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        studentService.init();
        userService.init();
        keywords = new Keywords(keywordService);
        keywords.init();
        DataFactory.buildData(tablesService);
        works = tablesService.getAllCourseworks();
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

    private void setTestModel(CourseworkCommand com, Model model, Long courseworkId){
        Coursework coursework = tablesService.getCourseworkById(courseworkId);
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
    
    private void setTestModel(DoubleCommand com, Model model, Long courseworkId, String studentId){
        Coursework coursework = tablesService.getCourseworkById(courseworkId);
        String[] categ = tablesService.getCategoriesNames(coursework.getId());
        String[] bands = tablesService.getAllBandsNames();
        List<List<List<String>>> crit = tablesService.getTable(coursework.getId());


        model.addAttribute("categ", categ);
        model.addAttribute("bands", bands);
        model.addAttribute("crit", crit);

        model.addAttribute("keywords", keywords);
        
        DoubleCommand command = new DoubleCommand();

        for (int i = 0; i < categ.length; i++){
            command.addCat();
            for(int j = 1; j <= crit.get(i).size(); j++){
                command.addCrit(i,"", "");
            }
        }
        
        command.setStudentID(studentId);

        if(com.getStudentID() == null){
            model.addAttribute( "command", command);
        } else {
            model.addAttribute("command", com);
        }

    }
    
    private OldMarks createOldMarks(Long courseworkId, CourseworkEntry c1, CourseworkEntry c2){
        Coursework coursework = tablesService.getCourseworkById(courseworkId);
        String[] categ = tablesService.getCategoriesNames(coursework.getId());
        String[] bands = tablesService.getAllBandsNames();
        List<List<List<String>>> crit = tablesService.getTable(coursework.getId());

        OldMarks command = new OldMarks();

        for (int i = 0; i < categ.length; i++){
            command.addCat();
            for(int j = 1; j <= crit.get(i).size(); j++){
                command.addCrit(i,"", "");
            }
        }
        
        command.setStudentID(c1.getStudent().getId());
        command.addOldEntry(c1, courseworkEntryService);
        command.addOldEntry(c2, courseworkEntryService);
        
        command.setCw(c1.getCoursework());
        
//        System.out.println(command);
        
        return command;
    }
    
    @GetMapping("/coursework")
    public String coursework(HttpServletRequest request, @ModelAttribute("courseworkRaw") CourseworkCommand command,
            @RequestParam("id") Coursework id, @ModelAttribute("error") String error, Model model, RedirectAttributes ra) {
        User u = addAttributes(request, model);
            Object courseworkId;
            if (command != null){
                request.getSession().setAttribute("type", id.getId());
                courseworkId = id.getId();
            } else {
                courseworkId = request.getSession().getAttribute("type");
            }
            model.addAttribute("id", tablesService.getCourseworkById((Long) courseworkId).getName());
            setTestModel(command, model, (Long) courseworkId);
            
            UserType type = getUserType(u);
            
            model.addAttribute("error", error);
            
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

        if(studentService.get(command.getStudentID()) == null){
            ra.addAttribute("id", request.getSession().getAttribute("type"));
            ra.addFlashAttribute("courseworkRaw", command);
            ra.addFlashAttribute("error", "Error: student does not exist in database.");
            return "redirect:/coursework";
        }
        
        boolean isDoubleLimit = courseworkEntryService.getDoubleMarkedEntries().stream()
                .anyMatch(i -> (i.get(0).getStudent().getId().equals(command.getStudentID()) && 
                        (i.get(0).getCoursework().getId()).equals(request.getSession().getAttribute("type"))) );
        
        if(isDoubleLimit){
            ra.addAttribute("id", request.getSession().getAttribute("type"));
            ra.addFlashAttribute("courseworkRaw", command);
            ra.addFlashAttribute("error", "Error: student/work pair has reached the double marking limit.");
            return "redirect:/coursework";
        }
        
        User u = addAttributes(request, model);
        
        boolean isMarkLimit = courseworkEntryService.getAllByStudent(studentService.get(command.getStudentID())).stream()
                .anyMatch(i -> (i.getCoursework().getId()).equals(request.getSession().getAttribute("type")) && 
                        i.getTeacher() == u);
        
        if(isMarkLimit){
            ra.addAttribute("id", request.getSession().getAttribute("type"));
            ra.addFlashAttribute("courseworkRaw", command);
            ra.addFlashAttribute("error", "Error: user has already entered a mark for this student/work.");
            return "redirect:/coursework";
        }
        
        ra.addFlashAttribute("command", command);
//        System.out.println(command);

        return "redirect:/reviewcoursework";
    }


    @GetMapping("/reviewcoursework")
    public String reviewcoursework(HttpServletRequest request, @ModelAttribute("command") CourseworkCommand command,
			Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("id", request.getSession().getAttribute("type"));


        setTestModel(command, model, (Long) request.getSession().getAttribute("type"));

        int[][] rs;
        rs = CalculateMarks.separateCategories(command);

        Long courseworkId = (Long)request.getSession().getAttribute("type");
        List<Float> weights = tablesService.getCategoriesWeights(courseworkId);

        model.addAttribute("courseworkRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getOverallScore(buildCategoryAverage(rs), weights));
        
        List<Integer> cat_rs = new ArrayList<>();
        
        for(int[] x : rs){
            cat_rs.add(CalculateMarks.getBandAverage(x));
        }
        
        model.addAttribute("cat_grades", cat_rs);
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
        User user = addAttributes(request, model);

        CourseworkCommand m = (CourseworkCommand) request.getSession().getAttribute("coursework");

        int[][] rs;
        rs = CalculateMarks.separateCategories(m);

        List<Integer> categoryAverage = buildCategoryAverage(rs);

        Long courseworkId = (Long)request.getSession().getAttribute("type");
        List<Float> weights = tablesService.getCategoriesWeights(courseworkId);
        Float overallScore = CalculateMarks.getOverallScore(categoryAverage, weights);

        ra.addFlashAttribute("id", m.studentID);
        ra.addFlashAttribute("grade", overallScore);

        try {
            insertCoursework(m, courseworkId, user, categoryAverage, overallScore, false);
        } catch(Exception e) {
            return "/error";
        }
        return "redirect:/resultPage";
    }

    private void insertCoursework(CourseworkCommand m, Long courseworkId, User user, List<Integer> categoryAverage, Float overallScore, Boolean doubleMarking) throws Exception {
        //If student is not in database, throw an Exception
        Student student = studentService.search(m.studentID);

        //Create courseworkEntry
        CourseworkEntry courseworkEntry = new CourseworkEntry(student, categoryAverage, overallScore, tablesService.getCourseworkById(courseworkId), user);
        courseworkEntry.setComment(m.overallComment);
        courseworkEntry.setResolvedDoubleMarking(doubleMarking);
        courseworkEntryService.addCourseworkEntry(courseworkEntry, user);

        List<Category> categories = tablesService.getCategories(courseworkId);
        for (int i = 0; i < categories.size(); i++) {
            CategoryEntry categoryEntry = new CategoryEntry(courseworkEntry, categories.get(i), categoryAverage.get(i));
            courseworkEntryService.addCategoryEntry(categoryEntry);
            List<Criterion> criteria = tablesService.getCriteria(categories.get(i).getId());
            for (int j = 0; j < criteria.size(); j++) {
                int chosen = CalculateMarks.getBand(m.vs.get(i).get(j));
                Band band = tablesService.getBandByOrder(chosen);
                Cell cell = tablesService.getCell(criteria.get(j).getId(), band.getId());
                String comment = m.v_comments.get(i).get(j);
                CellEntry cellEntry = new CellEntry(cell, categoryEntry);
                cellEntry.setComment(comment);
                courseworkEntryService.addCellEntry(cellEntry);
                System.out.println(cellEntry);
            }
        }
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
        
        List<List<CourseworkEntry>> results = new ArrayList<>();
        
        for(Coursework c : tablesService.getAllCourseworks()){
            results.add( courseworkEntryService.getAllByType(c.getId()) );
        }
        
        System.out.println("results = " + results.toString());
        
        model.addAttribute("results", results);
        model.addAttribute("courseworks", tablesService.getAllCourseworks());
        model.addAttribute("cwDropdown", tablesService.getAllCourseworks());
        model.addAttribute("ts", tablesService);
        model.addAttribute("ces", courseworkEntryService);
        model.addAttribute("filterId", -1);
        
        
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        UserType type = getUserType(u);
        if (type != UserType.TEACHER) return "redirect:/index";
        else return "showMarks";
    }

    @PostMapping("/showMarks")
    public String searchMarks(@ModelAttribute("command") ShowMarksCommand command, BindingResult binding,
			Model model, RedirectAttributes ra, HttpServletRequest request) {
        
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        if (type != UserType.TEACHER) return "redirect:/index";
        
        addAttributes(request, model);
        List<List<CourseworkEntry>> reports = new ArrayList<>();

        if (binding.hasErrors()) {
            System.out.println("binding had errors\n");
            return "/error";
        }

        model.addAttribute("command", command);
        System.out.println(command.cwType);
        
        if("".equals(command.search)){
            model.addAttribute("command", new ShowMarksCommand());
            
            if(command.cwType == -1){
                for(Coursework c : tablesService.getAllCourseworks()){
                    reports.add( courseworkEntryService.getAllByType(c.getId()) );
                }
            } else {
                reports.add( courseworkEntryService.getAllByType(command.cwType) );
            }

        } else {
            System.out.println(command.search);
            Student student = studentService.get(command.search);
            System.out.println(student);
            
            if(command.cwType == -1){
                for(Coursework c : tablesService.getAllCourseworks()){
                    List<CourseworkEntry> r = new ArrayList<>();
                    courseworkEntryService.getAllByType(c.getId())
                            .stream().filter(i -> i.getStudent() == student)
                            .forEach(r::add);
                    reports.add(r);
                }
            } else {
  
                List<CourseworkEntry> r = new ArrayList<>();
                courseworkEntryService.getAllByType(command.cwType)
                        .stream().filter(i -> i.getStudent() == student)
                        .forEach(r::add);
                reports.add(r);
            }
            

        }
        
        model.addAttribute("results", reports);
        
        if(command.cwType == -1) {
            model.addAttribute("courseworks", tablesService.getAllCourseworks());
        } else {
            model.addAttribute("courseworks", tablesService.getCourseworkById(command.cwType));
        }
        
        model.addAttribute("cwDropdown", tablesService.getAllCourseworks());
        model.addAttribute("ts", tablesService);
        model.addAttribute("ces", courseworkEntryService);
        model.addAttribute("filterId", command.cwType);

        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());

        return "showMarks";
    }

    private ShowMarksCommand buildMarksCommand(List<List<CourseworkEntry>> results){
        ShowMarksCommand command = new ShowMarksCommand();
        int cat = 0;
        for(List<CourseworkEntry> i : results){
            command.addCat();
            for(CourseworkEntry c : i){
                System.out.println("Trying to add " + c);
                command.addId(cat, c.getId(), courseworkEntryService);
            }
            cat++;
        }
        return command;
    }
    

    @GetMapping("/adminShowMarks")
    public String adminShowMarks(HttpServletRequest request, Model model) {
        User u = addAttributes(request, model);
        
        List<List<CourseworkEntry>> results = new ArrayList<>();
        
        for(Coursework c : tablesService.getAllCourseworks()){
            results.add( courseworkEntryService.getAllByType(c.getId()) );
        }
        
        System.out.println("results = " + results.toString());
        
        model.addAttribute("results", results);
        model.addAttribute("courseworks", tablesService.getAllCourseworks());
        model.addAttribute("cwDropdown", tablesService.getAllCourseworks());
        model.addAttribute("ts", tablesService);
        model.addAttribute("ces", courseworkEntryService);
        model.addAttribute("filterId", -1);
        System.out.println(buildMarksCommand(results));
        model.addAttribute("command", buildMarksCommand(results));
        
        
        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());
        UserType type = getUserType(u);
        if (type != UserType.ADMIN) return "redirect:/index";
        else return "adminShowMarks";
    }

    @RequestMapping(value = "/adminShowMarks",params="searchButton", method = RequestMethod.POST) 
    public String adminSearchMarks(@ModelAttribute("command") ShowMarksCommand command,
                                    BindingResult binding,
                                    Model model,
                                    RedirectAttributes ra,
                                    HttpServletRequest request) {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        if (type != UserType.ADMIN) return "redirect:/index";
        
        addAttributes(request, model);
        List<List<CourseworkEntry>> reports = new ArrayList<>();

        if (binding.hasErrors()) {
            System.out.println("binding had errors\n");
            return "/error";
        }

        model.addAttribute("command", command);
        System.out.println(command.cwType);
        
        if("".equals(command.search)){
            model.addAttribute("command", new ShowMarksCommand());
            
            if(command.cwType == -1){
                for(Coursework c : tablesService.getAllCourseworks()){
                    reports.add( courseworkEntryService.getAllByType(c.getId()) );
                }
            } else {
                reports.add( courseworkEntryService.getAllByType(command.cwType) );
            }

        } else {
            System.out.println(command.search);
            Student student = studentService.get(command.search);
            System.out.println(student);
            
            if(command.cwType == -1){
                for(Coursework c : tablesService.getAllCourseworks()){
                    List<CourseworkEntry> r = new ArrayList<>();
                    courseworkEntryService.getAllByType(c.getId())
                            .stream().filter(i -> i.getStudent() == student)
                            .forEach(r::add);
                    reports.add(r);
                }
            } else {
  
                List<CourseworkEntry> r = new ArrayList<>();
                courseworkEntryService.getAllByType(command.cwType)
                        .stream().filter(i -> i.getStudent() == student)
                        .forEach(r::add);
                reports.add(r);
            }
            

        }
        
        model.addAttribute("results", reports);
        
        if(command.cwType == -1) {
            model.addAttribute("courseworks", tablesService.getAllCourseworks());
        } else {
            model.addAttribute("courseworks", tablesService.getCourseworkById(command.cwType));
        }
        
        model.addAttribute("cwDropdown", tablesService.getAllCourseworks());
        model.addAttribute("ts", tablesService);
        model.addAttribute("ces", courseworkEntryService);
        model.addAttribute("filterId", command.cwType);

        System.out.println("Result size == " + courseworkEntryService.getAll().size());
        System.out.println(courseworkEntryService.getAll());

        return "adminShowMarks";

    }
    
    @RequestMapping(value = "/adminShowMarks",params="updateButton",method = RequestMethod.POST) 
    public String adminUpdateMarks(@ModelAttribute("command") ShowMarksCommand command,
                                    BindingResult binding,
                                    Model model,
                                    RedirectAttributes ra,
                                    HttpServletRequest request) {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        if (type != UserType.ADMIN) return "redirect:/index";

        return "adminShowMarks";

    }
    
    @GetMapping("/adminExportTable")
    public String adminExportTable(HttpServletRequest request, Model model, @ModelAttribute("error") String error, RedirectAttributes ra) {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        
        // -------- We need to check that there are no double marks before export
        
        List<CourseworkEntry> results = courseworkEntryService.getAll().stream()
                .filter(i -> courseworkEntryService.isEntryDoubleMarked(i))
                .collect(Collectors.toList());
        
        if(type != UserType.ADMIN) return "redirect:/index";
        
        if(!results.isEmpty() && error.isEmpty()){
            ra.addFlashAttribute("error", "Please resolve all double marks before exporting.");
            return "redirect:/adminExportTable";
        }
        
        else return "adminExportTable";
    }

    @PostMapping("/adminExportTable")
    public ResponseEntity<InputStreamResource>  adminExportTable(
                                    Model model,
                                    HttpServletRequest request)  throws IOException  {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        if (type != UserType.ADMIN) return null;
        
        System.out.println("----------------- Exporting of Table ------------------");
        
        List<CourseworkEntry> courseworks = (List<CourseworkEntry>) courseworkEntryService.getAll();
        ByteArrayInputStream in = ExcelGenerator.courseworksToExcel(courseworks, tablesService, courseworkEntryService);

        HttpHeaders headers = new HttpHeaders();
               headers.add("Content-Disposition", "attachment; filename=celfs_marks.xlsx");

        return ResponseEntity
                        .ok()
                        .headers(headers)
                        .body(new InputStreamResource(in));
    }
    
    @GetMapping("/showDoubleMarks")
    public String showDoubleMarks(HttpServletRequest request, Model model) {
        User u = addAttributes(request, model);
        UserType type = getUserType(u);
        
        List<List<CourseworkEntry>> results = courseworkEntryService.getDoubleMarkedEntries();
        
        model.addAttribute("results", results);
        
        List<OldMarks> allOldMarks = new ArrayList<>();
        
        for(List<CourseworkEntry> r : results){
            allOldMarks.add(createOldMarks(r.get(0).getCoursework().getId(), r.get(0), r.get(1)) );
        }
        request.getSession().setAttribute("allOldMarks", allOldMarks);
        
        if (type != UserType.ADMIN) return "redirect:/index";
        else return "showDoubleMarks";
    }
    
    @GetMapping("/doubleReview")
    public String doubleReview(HttpServletRequest request, @ModelAttribute("courseworkRaw") DoubleCommand command,
            @RequestParam("index") int index, Model model, RedirectAttributes ra) {
        User u = addAttributes(request, model);
            Object courseworkId;
            List<OldMarks> allOldMarks = (List<OldMarks>) request.getSession().getAttribute("allOldMarks");
//            System.out.println("command from ra = " + command);
            
            if (command.getStudentID() != null){
                courseworkId = request.getSession().getAttribute("type");
            } else {
                courseworkId = allOldMarks.get(index).getCw().getId();
                request.getSession().setAttribute("type", courseworkId);
//                System.out.println("command from list = " + allOldMarks.get(index));
            } 
            
            setTestModel(command, model, (Long) courseworkId, allOldMarks.get(index).getStudentID());
            
            request.getSession().setAttribute("index", index);
            
            model.addAttribute("id", tablesService.getCourseworkById((Long) courseworkId).getName());
            model.addAttribute("oldMarks", allOldMarks.get(index));
            
            UserType type = getUserType(u);
            
            if (type != UserType.ADMIN) return "redirect:/index";
            else return "doubleReview";
    }
    
    @PostMapping("/doubleReview")
    public String submitDouble(@ModelAttribute("command") DoubleCommand command, BindingResult binding,
			HttpServletRequest request, Model model, RedirectAttributes ra ) {
        addAttributes(request, model);
        if (binding.hasErrors()) {
            return "/error";
        }

        ra.addFlashAttribute("command", command);
//        System.out.println("Post command = " + command);

        return "redirect:/reviewDoubleCoursework";
    }


    @GetMapping("/reviewDoubleCoursework")
    public String reviewDoubleCoursework(HttpServletRequest request, @ModelAttribute("command") DoubleCommand command,
			Model model) {
        User u = addAttributes(request, model);
        model.addAttribute("id", request.getSession().getAttribute("type"));

        List<OldMarks> allOldMarks = (List<OldMarks>) request.getSession().getAttribute("allOldMarks");
        int index = (int) request.getSession().getAttribute("index");
        
        setTestModel(command, model, (Long) request.getSession().getAttribute("type"), allOldMarks.get(index).getStudentID());

        int[][] rs;
        rs = CalculateMarks.separateCategories(command);

        Long courseworkId = (Long)request.getSession().getAttribute("type");
        List<Float> weights = tablesService.getCategoriesWeights(courseworkId);

        model.addAttribute("courseworkRaw", command);
        model.addAttribute("totalGrade", CalculateMarks.getOverallScore(buildCategoryAverage(rs), weights));
        
        List<Integer> cat_rs = new ArrayList<>();
        
        for(int[] x : rs){
            cat_rs.add(CalculateMarks.getBandAverage(x));
        }
        
        model.addAttribute("cat_grades", cat_rs);
        request.getSession().setAttribute("coursework", command);

        CalculateMarks calc = new CalculateMarks();

        model.addAttribute("Calc", calc);

        UserType type = getUserType(u);
        
        if (type != UserType.ADMIN) return "redirect:/index";
        else return "reviewDoubleMarks";
    }

    @RequestMapping(value="/reviewDoubleCoursework",params="editButton",method=RequestMethod.POST)
    public String editDouble(HttpServletRequest request, @ModelAttribute("id") String id, Model model, RedirectAttributes ra ) {
        addAttributes(request, model);

        DoubleCommand m = (DoubleCommand) request.getSession().getAttribute("coursework");

        ra.addFlashAttribute("courseworkRaw", m);
        ra.addAttribute("index", request.getSession().getAttribute("index"));

        return "redirect:/doubleReview";
    }

    @RequestMapping(value="/reviewDoubleCoursework",params="submitButton",method=RequestMethod.POST)
    public String finalSubmitDouble(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        User user = addAttributes(request, model);

        DoubleCommand m = (DoubleCommand) request.getSession().getAttribute("coursework");

        int[][] rs;
        rs = CalculateMarks.separateCategories(m);

        List<Integer> categoryAverage = buildCategoryAverage(rs);

        Long courseworkId = (Long)request.getSession().getAttribute("type");
        List<Float> weights = tablesService.getCategoriesWeights(courseworkId);
        Float overallScore = CalculateMarks.getOverallScore(categoryAverage, weights);

        ra.addFlashAttribute("id", m.studentID);
        ra.addFlashAttribute("grade", overallScore);

        try {
            courseworkEntryService.deleteOldEntries(m.studentID, courseworkId);
            insertCoursework(new CourseworkCommand(m.new_vs, m.new_v_comments, m.studentID), courseworkId, user, categoryAverage, overallScore, true);
        } catch(Exception e) {
            return "/error";
        }

        return "redirect:/resultPage";
    }
}
