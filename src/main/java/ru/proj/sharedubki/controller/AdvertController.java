package ru.proj.sharedubki.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.service.AdvertService;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @GetMapping("/")
    public String adverts(@RequestParam(name = "title", required = false) String title, Model model) {

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


    // !ToDo? почему в requestparam везде file1. Solved.
    @PostMapping("/advert/create")
    public String createAdvert(@RequestParam("file1") MultipartFile file1,
                               @RequestParam("file2") MultipartFile file2,
                               @RequestParam("file3") MultipartFile file3,
                               Advert advert) throws IOException {
        System.err.println(advert);
        advertService.saveAdvert(advert, file1, file2, file3);
        return "redirect:/";
    }

    @PostMapping("/advert/delete/{id}")
    public String deleteAdvert(@PathVariable Long id){
        advertService.deleteAdvert(id);
        return "redirect:/";
    }

}
