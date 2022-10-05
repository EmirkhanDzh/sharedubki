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
import java.util.List;


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
    public String adverts(
            @RequestParam(name = "searchWord", required = false) String searchWord,
            @RequestParam(name = "searchCorpus", required = false) Integer searchCorpus,
            @RequestParam(name = "searchCategory", required = false) String searchCategory,
            Principal principal,
            Model model
    ) {
        System.err.println("category:" + searchCategory);
        User user = userService.getUserByPrincipal(principal);
        List<Advert> adverts = advertService.getAdverts(searchWord, searchCorpus, searchCategory);
        model.addAttribute("user", user);
        model.addAttribute("adverts", adverts);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("searchCorpus", searchCorpus);
        model.addAttribute("searchCategory", searchCategory);
        return "adverts";
    }

    @GetMapping("/advert/{id}")
    public String advertInfo(@PathVariable Long id, Model model, Principal principal) {
        Advert advert = advertService.getAdvertById(id);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("advert", advert);
        model.addAttribute("images", advertService.getNotEmptyImages(advert));
        model.addAttribute("authorAdvert", advert.getUser());
        return "advert-info";
    }


    // ToDo? почему в requestparam везде file1. Solved.
    @PostMapping("/advert/create")
    public String createAdvert(@RequestParam("file1") MultipartFile file1,
                               @RequestParam("file2") MultipartFile file2,
                               @RequestParam("file3") MultipartFile file3,
                               Advert advert,
                               Principal principal) throws IOException {
        advertService.saveAdvert(advert, principal, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/advert/{id}/delete")
    public String deleteAdvert(@PathVariable Long id, Principal principal) {
        advertService.deleteAdvert(userService.getUserByPrincipal(principal), id);
        return "redirect:/my/adverts";
    }

    @GetMapping("/advert/{id}/edit")
    public String showEditingWindow(@PathVariable Long id, Principal principal, Model model) {

        Advert advert = advertService.getAdvertById(id);
        try {
            User user = userService.getUserByPrincipal(principal);
            if (principal != null && user.getId().equals(advert.getUser().getId())) {
                model.addAttribute("advert", advert);
                model.addAttribute("user", user);
                model.addAttribute("images", advert.getImages());
                return "advert-edit";
            } else {
                if (principal == null) {
                    return "redirect:/login";
                } else {
                    return "redirect:/";
                }
            }

        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/advert/{id}/edit")
    public String editAdvert(@RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2,
                             @RequestParam("file3") MultipartFile file3,
                             Advert advertDataFromUser, @PathVariable Long id, Principal principal) throws IOException {
        advertService.editAdvert(id, advertDataFromUser, principal, file1, file2, file3);
        return "redirect:/my/adverts";
    }

    @GetMapping("/my/adverts")
    public String showUserAdverts(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("adverts", user.getAdverts());
        return "user-adverts";
    }
}
