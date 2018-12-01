package uk.ac.bris.celfs.website;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private MarksRepository markRepository;
    
    @Autowired
    private MrrRawRepository mrrRawRepository;
    
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
    public String mrr() {
        return "mrr";
    }

    @PostMapping(value = "/mrr")
    public String submitMrr(@RequestParam("studentID")                  String studentID,
                            @RequestParam("response_comment")           String response_comment,
                            @RequestParam("response")                   String response,
                            @RequestParam("method_comment")             String method_comment,
                            @RequestParam("method")                     String method,
                            @RequestParam("results_comment")            String results_comment,
                            @RequestParam("results")                    String results,
                            @RequestParam("discussion_comment")         String discussion_comment,
                            @RequestParam("discussion")                 String discussion,
                            @RequestParam("synthesis_comment")          String synthesis_comment,
                            @RequestParam("synthesis")                  String synthesis,
                            @RequestParam("reasoning_comment")          String reasoning_comment,
                            @RequestParam("reasoning")                  String reasoning,
                            @RequestParam("control_comment")            String control_comment,
                            @RequestParam("control")                    String control,
                            @RequestParam("errors_comment")             String errors_comment,
                            @RequestParam("errors")                     String errors,
                            @RequestParam("nounphrases_comment")        String nounphrases_comment,
                            @RequestParam("nounphrases")                String nounphrases,
                            @RequestParam("vocabulary_comment")         String vocabulary_comment,
                            @RequestParam("vocabulary")                 String vocabulary,
                            @RequestParam("wordchoice_comment")         String wordchoice_comment,
                            @RequestParam("wordchoice")                 String wordchoice,
                            @RequestParam("style_comment")              String style_comment,
                            @RequestParam("style")                      String style,
                            @RequestParam("sentencestructure_comment")  String sentencestructure_comment,
                            @RequestParam("sentencestructure")          String sentencestructure,
                            @RequestParam("organisation_comment")       String organisation_comment,
                            @RequestParam("organisation")               String organisation,
                            @RequestParam("development_comment")        String development_comment,
                            @RequestParam("development")                String development,
                            @RequestParam("cohesivedevices_comment")    String cohesivedevices_comment,
                            @RequestParam("cohesivedevices")            String cohesivedevices,
                            @RequestParam("conclusion_comment")         String conclusion_comment,
                            @RequestParam("conclusion")                 String conclusion,
                            @RequestParam("presentation_comment")       String presentation_comment,
                            @RequestParam("presentation")               String presentation,
                            @RequestParam("overallComment")             String overallComment,
                            BindingResult binding,
                            RedirectAttributes attr) {
        if (binding.hasErrors()) {
            return "/error";
        }
        MrrRaw myMrrRaw = new MrrRaw();
        myMrrRaw.studentID = studentID;
        myMrrRaw.response_comment = response_comment;
        myMrrRaw.response = response;
        method_comment;
        String method;
        String results_comment;
        String results;
        String discussion_comment;
        String discussion;
        String synthesis_comment;
        String synthesis;
        String reasoning_comment;
        String reasoning;
        String control_comment;
        String control;
        String errors_comment;
        String errors;
        String nounphrases_comment;
        String nounphrases;
        String vocabulary_comment;
        String vocabulary;
        String wordchoice_comment;
        String wordchoice;
        String style_comment;
        String style;
        String sentencestructure_comment;
        String sentencestructure;
        String organisation_comment;
        String organisation;
        String development_comment;
        String development;
        String cohesivedevices_comment;
        String cohesivedevices;
        String conclusion_comment;
        String conclusion;
        String presentation_comment;
        String presentation;
        String overallComment;
        mrrRawRepository.save(myMrrRaw);
        // attr.addFlashAttribute("message", "Thank you for your quote.");
        return "redirect:/reviewmrr";
    }
    
    
    @GetMapping("/reviewmrr")
    public String reviewmrr() {
        return "reviewmrr";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
