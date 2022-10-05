package ru.proj.sharedubki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {

        switch(userService.createUser(user)){
            case 1: {
                model.addAttribute("errorMessage", "Пользователь с таким email: " + user.getEmail() + " уже существует!!!");
                return "registration";
            }
            case 2: {
                model.addAttribute("errorMessage","Пользователь с таким номером телефона: " + user.getPhoneNumber() + " уже существует!!!");
                return "registration";
            }
            default: return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String showUserPersonalPage(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/{user}")
    public String showUserProfile(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("adverts", user.getAdverts());
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        return "user-info";
    }

    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }

}
