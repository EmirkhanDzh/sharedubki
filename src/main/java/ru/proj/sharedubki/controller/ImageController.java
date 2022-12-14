package ru.proj.sharedubki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.service.AdvertService;
import ru.proj.sharedubki.service.ImageService;
import ru.proj.sharedubki.service.UserService;

import java.io.ByteArrayInputStream;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final UserService userService;
    private final AdvertService advertService;
    private final ImageService imageService;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytesOfImage())));
    }

    @PostMapping("/images/{id}/delete")
    private String deleteImage(@PathVariable Long id, @RequestParam Long advertId, Principal principal) {
        Advert advert = advertService.getAdvertById(advertId);
        User user = userService.getUserByPrincipal(principal);
        if (imageService.deleteImageFromAdvert(id, advert, user)) {
            return "redirect:/advert/" + advertId + "/edit";
        } else {
            return "redirect:/";
        }
    }
}
