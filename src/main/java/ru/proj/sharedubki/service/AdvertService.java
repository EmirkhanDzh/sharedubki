package ru.proj.sharedubki.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.AdvertRepository;
import ru.proj.sharedubki.repository.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class AdvertService {

    final int numOfImages = 3;

    private final UserService userService;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    public AdvertService(UserService userService, AdvertRepository advertRepository, UserRepository userRepository) {
        this.userService = userService;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
    }

    public List<Advert> getAdverts(String title) {
        if (title != null) {
            return advertRepository.findByTitle(title);
        }
        return advertRepository.findAll();
    }

    public void saveAdvert(Advert advert, Principal principal, MultipartFile... file) throws IOException {

        advert.setUser(userService.getUserByPrincipal(principal));

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
        log.info("saving new Advert with Title: {}; User email: {}", advert.getTitle(), advert.getUser().getEmail());
        // ToDo один раз сохранить в БД
        Advert advertFromDb = advertRepository.save(advert);
        List<Image> imagesList = advertFromDb.getImages();
        if(!imagesList.isEmpty()) {
            advertFromDb.setPreviewImageId(imagesList.stream().findFirst().get().getId());
        }
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

    public void deleteAdvert(User user, Long id) {
        Advert advert = advertRepository.findById(id)
                .orElse(null);
        if (advert != null) {
            if (advert.getUser().getId().equals(user.getId())) {
                advertRepository.delete(advert);
                log.info("Advert with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this advert with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Advert with id = {} is not found", id);
        }    }

    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id).orElse(null);
    }

    public void editAdvert() {
    }
}
