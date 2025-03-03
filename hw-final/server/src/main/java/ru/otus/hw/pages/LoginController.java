package ru.otus.hw.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final static String LOGIN_FILE = "views/login/login";

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN_FILE;
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("error", true);
        return LOGIN_FILE;
    }

}
