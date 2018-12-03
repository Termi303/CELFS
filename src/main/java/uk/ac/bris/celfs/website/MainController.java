package uk.ac.bris.celfs.website;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private MarksRepository markRepository;
    
    // @Autowired
    // private MrrRawRepository mrrRawRepository;
    
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
    public String mrr(Model model) {
        model.addAttribute( "command", new MrrCommand());
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
    public String reviewmrr(@ModelAttribute("command") MrrCommand command,
			Model model) {
        
        model.addAttribute("mrrRaw", command);
        
        return "reviewmrr";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
