package ru.proj.sharedubki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.proj.sharedubki.model.Advert;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<Advert> findByTitle(String title);
}
