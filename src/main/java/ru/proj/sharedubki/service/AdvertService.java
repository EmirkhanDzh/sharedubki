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

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    public AdvertService(AdvertRepository advertRepository, UserRepository userRepository) {
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

        advert.setUser(getUserByPrincipal(principal));

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
        advertFromDb.setPreviewImageId(advertFromDb.getImages().get(0).getId());
        // ?
        advertRepository.save(advertFromDb);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal != null)
        return userRepository.findByEmail(principal.getName());
        else
            return new User();
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
