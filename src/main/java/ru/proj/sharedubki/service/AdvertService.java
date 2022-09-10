package ru.proj.sharedubki.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.repository.AdvertRepository;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AdvertService {

    final int numOfImages = 3;

    private final AdvertRepository advertRepository;

    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public List<Advert> getAdverts(String title) {
        if (title != null) {
            return advertRepository.findByTitle(title);
        }
        return advertRepository.findAll();
    }

    public void saveAdvert(Advert advert, MultipartFile... file) throws IOException {

        Image[] images = new Image[3];
        for(int i = 0; i < numOfImages; ++i) {

            if(file[i].getSize() != 0) {
                images[i] = convertFileToImageEntity(file[i]);
                if(i == 0) {
                    images[0].setPreviewImage(true);
                }
                advert.addImageToAdvert(images[i]);
            }
        }
        log.info("saving new Advert with Title: {}; Author: {}", advert.getTitle(), advert.getAuthorName());
        // ToDo один раз сохранить в БД
        Advert advertFromDb = advertRepository.save(advert);
        advertFromDb.setPreviewImageId(advertFromDb.getImages().get(0).getId());
        // ?
        advertRepository.save(advertFromDb);
    }

    private Image convertFileToImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytesOfImage(file.getBytes());
        return image;
    }

    public void deleteAdvert(Long id) {

        advertRepository.deleteById(id);
    }

    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id).orElse(null);
    }
}
