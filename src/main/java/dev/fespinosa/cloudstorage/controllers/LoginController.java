package dev.fespinosa.cloudstorage.controllers;

import dev.fespinosa.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(User user) {
        return "login";
    }

}
