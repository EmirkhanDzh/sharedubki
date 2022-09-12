package ru.proj.sharedubki.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.AdvertRepository;
import ru.proj.sharedubki.service.AdvertService;
import ru.proj.sharedubki.service.UserService;

import java.io.IOException;
import java.security.Principal;


@Controller
public class AdvertController {

    private final AdvertService advertService;
    private final UserService userService;
    private final AdvertRepository advertRepository;


    public AdvertController(AdvertService advertService, UserService userService, AdvertRepository advertRepository) {
        this.advertService = advertService;
        this.userService = userService;
        this.advertRepository = advertRepository;
    }

    @GetMapping("/")
    public String adverts(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) {

        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("adverts", advertService.getAdverts(title));
        return "adverts";
    }

    @GetMapping("advert/{id}")
    public String advertInfo(@PathVariable Long id, Model model) {
        Advert advert = advertService.getAdvertById(id);

        model.addAttribute("advert", advert);
        model.addAttribute("images", advert.getImages());

        return "advert-info";
    }


    // ToDo? почему в requestparam везде file1. Solved.
    @PostMapping("/advert/create")
    public String createAdvert(@RequestParam("file1") MultipartFile file1,
                               @RequestParam("file2") MultipartFile file2,
                               @RequestParam("file3") MultipartFile file3,
                               Advert advert,
                               Principal principal) throws IOException {
        System.err.println(advert);
        advertService.saveAdvert(advert, principal, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/advert/delete/{id}")
    public String deleteAdvert(@PathVariable Long id, Principal principal){
        advertService.deleteAdvert(userService.getUserByPrincipal(principal), id);
        return "redirect:/";
    }

    @GetMapping("/my/products")
    public String showUserAdverts(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("adverts", user.getAdverts());
        return "user-adverts";
    }
}
