package ru.proj.sharedubki.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.proj.sharedubki.enums.Role;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.service.AdvertService;
import ru.proj.sharedubki.service.UserService;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService, AdvertService advertService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Principal principal, Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "admin";
    }

    @PostMapping("/admin/user/{id}/ban")
    public String banUser(@PathVariable("id") Long id) {
        userService.banUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{user-id}/edit")
    public String showUserEdit(@PathVariable("user-id") User user, Model model, Principal principal){
        model.addAttribute("user", user);
//        model.addAttribute("superAdmin", userService.getUserByPrincipal(principal));
        model.addAttribute("roles", Role.values());
        return "admin-user-role-edit";
    }

    @PostMapping("/admin/user/edit")
    public String editUser(@RequestParam("user-id") Long userId, @RequestParam Map<String, String> form) {
        User user = userService.getUserById(userId);
        if(user != null) {
            userService.changeUserRoles(user, form);
        }
        return "redirect:/admin";
    }

    @PostConstruct
    public void createSuperAdmin() {
        userService.createSuperAdmin();
    }

}
