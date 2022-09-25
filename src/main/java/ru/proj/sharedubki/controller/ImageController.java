package ru.proj.sharedubki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.AdvertRepository;
import ru.proj.sharedubki.repository.ImageRepository;
import ru.proj.sharedubki.service.AdvertService;
import ru.proj.sharedubki.service.ImageService;
import ru.proj.sharedubki.service.UserService;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final AdvertController advertController;
    private final UserService userService;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final AdvertService advertService;


    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytesOfImage())));
    }

    int i = 0;

    @PostMapping("/images/{id}/delete")
    private String deleteImage(@PathVariable Long id, @RequestParam Long advertId, Principal principal, Model model) {
        Advert advert = advertService.getAdvertById(advertId);
        User user = userService.getUserByPrincipal(principal);
        if (principal != null && user.getId().equals(advert.getUser().getId())) {
            Image image = imageService.getImageById(id);
            if (image != null) {
                if(image.isPreviewImage()) {
                    advert.setPreviewImageId(null);
                }
                advert.getImages().removeIf(img -> Objects.equals(img.getId(), image.getId()));
                imageRepository.deleteById(id);
            }
            return "redirect:/advert/"+advertId+"/edit";
        } else {

            return "redirect:/";
        }
    }
}
