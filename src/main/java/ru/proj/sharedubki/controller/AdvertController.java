package ru.proj.sharedubki.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.proj.sharedubki.service.AdvertService;


@Controller
@RequiredArgsConstructor
public class AdvertController {

    private final AdvertService advertService;

    @GetMapping("/")
    public String adverts(Model model) {

        model.addAttribute("adverts", advertService.getAdverts());
        return "adverts";
    }


}
