package ru.proj.sharedubki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.proj.sharedubki.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> { }
