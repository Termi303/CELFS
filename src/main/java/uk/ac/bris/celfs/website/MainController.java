package uk.ac.bris.celfs.website;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        
        model.addAttribute("mrrRaw", command);
        request.getSession().setAttribute("mrr", command);
        
        return "reviewmrr";
    }    
    
    @RequestMapping(value="/reviewmrr",params="editButton",method=RequestMethod.POST)
    public String editMrr(HttpServletRequest request, Model model, RedirectAttributes ra ) {
        
        MrrCommand m = (MrrCommand) request.getSession().getAttribute("mrr");
        System.out.println(m);
        
        ra.addFlashAttribute("mrrRaw", m);
        
        return "redirect:/mrr";
    }
    
    @RequestMapping(value="/reviewmrr",params="submitButton",method=RequestMethod.POST)
    public String submitMrr(Model model, RedirectAttributes ra ) {
        return "redirect:/resultPage";
    }
    
    

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/resultPage")
    public String resultPage() {
        return "resultPage";
    }
}
