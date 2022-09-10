package ru.proj.sharedubki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {

        if(!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
