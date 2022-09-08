package ru.proj.sharedubki.service;

import org.springframework.stereotype.Service;
import ru.proj.sharedubki.model.Advert;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertService {

    Long id = 0L;

    private List<Advert> advertList = new ArrayList<>();

    {
        System.err.println("выводим инфу: " + advertList);
        advertList.add(new Advert(1l, "food", "muesli", 50, 2));
        advertList.add(new Advert(2l, "cloth", "jeens", 1000, 3));
        System.err.println("выводим инфу: " + advertList);
    }

    public List<Advert> getAdverts() {
        return advertList;
    }

    public void saveAdvert(Advert advert) {
        advert.setId(id++);
        advertList.add(advert);
    }

    public void deleteAdvert(Long id) {
        advertList.removeIf(advert -> advert.getId().equals(id));
    }
}
