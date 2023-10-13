package com.bitlab.final_project.conroller;

import com.bitlab.final_project.models.User;
import com.bitlab.final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@EnableMethodSecurity
public class HomeController {


    @Autowired
    private UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/")
    public String homePage(){
        return "home";
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginPage(){
        return "login";}

    @PreAuthorize("isAnonymous()")
    @GetMapping("/sign-up")
    public String signUpPage(){
        return "sign-up";
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/sign-up")
    public String signUp(User user, @RequestParam(name = "re_password") String rePassword){

        return "redirect:/";
    }





    @PreAuthorize("isAuthenticated()")
    @GetMapping("/panel")
    public String panel(){
        return "panel";
    }
    @GetMapping("/rating")
    public String rating(){
        return "rating";
    }
    @GetMapping("/support")
    public String support(){
        return "support";
    }
    @PostMapping("/sign-out")
    public String signOut(){
        return "redirect:/";
    }
    @GetMapping("/settings")
    public String settings(){
        return "settings";
    }
    @PostMapping("/delete-account")
    public String delete(){
        return "redirect:/";
    }
    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }
    @GetMapping("/paragraph")
    public String paragraph(){
        return "paragraph";
    }
    @GetMapping("/question")
    public String question(){
        return "question";
    }
}
