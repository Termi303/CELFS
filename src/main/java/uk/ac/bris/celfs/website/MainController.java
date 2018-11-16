package uk.ac.bris.celfs.website;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private MarksRepository markRepository;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/marks")
    public String showBeardForm(Mark mark, Model model) {
        model.addAttribute("marks", markRepository.findAll());
        return "marks";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/mrr")
    public String mrr() {
        return "mrr";
    }

    @PostMapping(value = "/mark")
    public String submitContact(@Valid Mark mark, BindingResult binding, RedirectAttributes attr) {
        if (binding.hasErrors()) {
            return "/marks";
        }
        markRepository.save(mark);
        attr.addFlashAttribute("message", "Thank you for your quote.");
        return "redirect:/marks";
    }
}
