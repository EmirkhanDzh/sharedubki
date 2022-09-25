package ru.proj.sharedubki.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImageById(Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        return image;
    }
}
