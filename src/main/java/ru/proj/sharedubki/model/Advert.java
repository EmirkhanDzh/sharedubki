package ru.proj.sharedubki.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="adverts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="type")
    private String type;
    @Column(name="title")
    private String title;
    @Column(name="price")
    private int price;
    @Column(name="corpus")
    private int corpus;
    @Column(name="authorName")
    private String authorName;
    @Column(name="description", columnDefinition = "text")
    private String description;
    // private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "advert")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime createdDate;

    @PrePersist
    private void init() {
        createdDate = LocalDateTime.now();
    }

    public void addImageToAdvert(Image image) {
        image.setAdvert(this);
        images.add(image);
    }
}
