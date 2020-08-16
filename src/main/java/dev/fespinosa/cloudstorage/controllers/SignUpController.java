package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.User;
import dev.fespinosa.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {


    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignUpPage(User user, Model model) {
        return "signup";
    }


    @PostMapping("/signup")
    public String signUp(User user, Model model) {
        int usersCreated = userService.createUser(user);
        if (usersCreated > 0) {
            model.addAttribute("userCreated", true);
        } else {
            model.addAttribute("error", true);
        }
        return "signup";
    }

}
