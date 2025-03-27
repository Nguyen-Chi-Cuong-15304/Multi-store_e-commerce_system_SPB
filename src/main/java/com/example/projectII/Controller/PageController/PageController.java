package com.example.projectII.Controller.PageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectII.Service.RegisterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/all")
public class PageController {
    @Autowired
    private RegisterService registerService;

    @GetMapping("/")
    public String home() {
        return "homepage";
    }
    @GetMapping("/register")
    public String registerPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "message", required = false) String message,
                               Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }
    @PostMapping("/perform_register")
    public String performRegister(@RequestParam("fullname") String fullName,
                                  @RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  @RequestParam("role") String role, Model model) {
        try {
            registerService.register(fullName, username, password, role);
            return "redirect:/all/login?message=Registration successful, please login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }
    
    
    
}
