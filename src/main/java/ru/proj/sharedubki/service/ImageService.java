package ru.proj.sharedubki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.ImageRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImageById(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        return image;
    }

    public boolean deleteImageFromAdvert(Long imageId, Advert advert, User user) {
        if (user != null && user.getId().equals(advert.getUser().getId())) {
            Image image = getImageById(imageId);
            if (image != null) {
                if (image.isPreviewImage()) {
                    advert.setPreviewImageId(null);
                }
                advert.getImages().removeIf(img -> Objects.equals(img.getId(), image.getId()));
                imageRepository.deleteById(imageId);
                return true;
            }
        }
        return false;
    }
}
