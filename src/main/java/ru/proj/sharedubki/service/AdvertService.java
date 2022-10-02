package ru.proj.sharedubki.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.proj.sharedubki.model.Advert;
import ru.proj.sharedubki.model.Image;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.AdvertRepository;
import ru.proj.sharedubki.repository.ImageRepository;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdvertService {

    final int numOfImages = 3;
    private final UserService userService;
    private final AdvertRepository advertRepository;
    private final ImageRepository imageRepository;

    public AdvertService(UserService userService, AdvertRepository advertRepository, ImageRepository imageRepository) {
        this.userService = userService;
        this.advertRepository = advertRepository;
        this.imageRepository = imageRepository;
    }

    public List<Advert> getAdverts(String searchWord, Integer searchCorpus) {
        List<Advert> adverts = advertRepository.findAll();
        if (searchWord != null || searchCorpus != null) {

            if (searchWord != null && !searchWord.trim().isEmpty()) {
                String searchWordTrimmed = searchWord.trim().toLowerCase();
                if (searchCorpus != null) {
                    adverts.removeIf(ad -> ad.getCorpus() != searchCorpus.intValue() || !ad.getTitle().toLowerCase(Locale.ROOT).contains(searchWordTrimmed) && !ad.getDescription().toLowerCase().contains(searchWordTrimmed));
                } else {
                    adverts.removeIf(ad -> !ad.getTitle().toLowerCase(Locale.ROOT).contains(searchWordTrimmed) && !ad.getDescription().toLowerCase().contains(searchWordTrimmed));
                }
            }
            else {
                if(searchCorpus != null) {
                    adverts.removeIf(ad -> ad.getCorpus() != searchCorpus.intValue());
                }
            }
        }
        return adverts;
    }


    public List<Advert> getAdvertsByCategory(String title) {
        if (title != null) {
            return advertRepository.findByTitle(title);
        }
        return advertRepository.findAll();
    }

    public void saveAdvert(Advert advert, Principal principal, MultipartFile... files) throws IOException {

        advert.setUser(userService.getUserByPrincipal(principal));

        addImagesToNewAdvert(advert, files);

        log.info("saving new Advert with Title: {}; User email: {}", advert.getTitle(), advert.getUser().getEmail());
        // ToDo один раз сохранить в БД
        Advert advertFromDb = advertRepository.save(advert);
        List<Image> imagesList = advertFromDb.getImages();
        if (!imagesList.isEmpty()) {
            advertFromDb.setPreviewImageId(imagesList.stream().findFirst().get().getId());
        }
        advertRepository.save(advertFromDb);
    }

    private void addImagesToNewAdvert(Advert advert, MultipartFile... files) throws IOException {
        Image[] images = new Image[3];
        for (int i = 0, j = 0; i < numOfImages; ++i) {
            if (files[i].getSize() != 0) {
                images[i] = convertFileToImageEntity(files[i]);
                if (j == 0) {
                    images[i].setPreviewImage(true);
                } else {
                    images[i].setPreviewImage(false);
                }
                advert.addImageToAdvert(images[i]);
                j++;
            }
        }
    }

    private void updateImagesInExistentAdvert(Advert advert, MultipartFile... files) throws IOException {
        List<Image> images = advert.getImages();
        Image[] imagesFromUser = new Image[3];
        for (int i = 0, j = 0; i < numOfImages && j < numOfImages; ++i, ++j) {
            if (files[i].getSize() != 0) {
                imagesFromUser[i] = convertFileToImageEntity(files[i]);
                if ((i + 1) <= images.size()) {
                    if (!imagesFromUser[i].getSize().equals(images.get(i).getSize()) || !Arrays.equals(imagesFromUser[i].getBytesOfImage(), images.get(i).getBytesOfImage())) {
                        updateImageWithFileData(images.get(i), imagesFromUser[i]);
                        imageRepository.save(images.get(i));
                    }
                } else {
                    advert.addImageToAdvert(imagesFromUser[i]);
                }
            }
        }
        images = advert.getImages();
        for (int i = 0; i < images.size(); ++i) {
            if (i == 0) {
                images.get(i).setPreviewImage(true);
                advert.setPreviewImageId(images.get(i).getId());
            } else {
                images.get(i).setPreviewImage(false);
            }
        }

    }

    private void updateImageWithFileData(Image imageToUpdate, Image imageData) {
        imageToUpdate.setName(imageData.getName());
        imageToUpdate.setOriginalFileName(imageData.getOriginalFileName());
        imageToUpdate.setContentType(imageData.getContentType());
        imageToUpdate.setSize(imageData.getSize());
        imageToUpdate.setBytesOfImage(imageData.getBytesOfImage());
    }

    public void editAdvert(Long id, Advert advertFromUser, Principal principal, MultipartFile... files) throws IOException {
        Advert advertById = getAdvertById(id);
        User user = userService.getUserByPrincipal(principal);
        if (advertById != null && advertFromUser != null) {
            if (advertById.getUser().getId().equals(user.getId())) {
                if (!advertById.getTitle().equals(advertFromUser.getTitle())) {
                    advertById.setTitle(advertFromUser.getTitle());
                }
                if (advertById.getPrice() != advertFromUser.getPrice()) {
                    advertById.setPrice(advertFromUser.getPrice());
                }
                if (advertById.getCorpus() != advertFromUser.getCorpus()) {
                    advertById.setCorpus(advertFromUser.getCorpus());
                }
                if (!advertById.getDescription().equals(advertFromUser.getDescription())) {
                    advertById.setDescription(advertFromUser.getDescription());
                }
                /*if (!advertById.getType().equals(advertFromUser.getType())) {
                    advertById.setType(advertFromUser.getType());
                }*/

                updateImagesInExistentAdvert(advertById, files);


                log.info("updating new Advert with Title: {}; User email: {}", advertById.getTitle(), advertById.getUser().getEmail());
                // ToDo один раз сохранить в БД
                Advert advertFromDb = advertRepository.save(advertById);
                List<Image> imagesList = advertFromDb.getImages();
                if (!imagesList.isEmpty()) {
                    for (var img : imagesList) {
                        if (img != null) {
                            advertFromDb.setPreviewImageId(img.getId());
                            break;
                        }
                    }
                }
                advertRepository.save(advertById);
                log.info("Advert with id = {} was updated", id);
            } else {
                log.error("User: {} haven't this advert with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Advert with id = {} is not found", id);
        }
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
                user.getAdverts().removeIf(ad -> ad.getId() == advert.getId());
                advertRepository.delete(advert);
                log.info("Advert with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this advert with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Advert with id = {} is not found", id);
        }
    }

    public Advert getAdvertById(Long id) {
        return advertRepository.findById(id).orElse(null);
    }


    public List<Image> getNotEmptyImages(Advert advert) {
        List<Image> images = advert.getImages().stream().filter(Objects::nonNull).collect(Collectors.toList());
        return images;
    }
}
