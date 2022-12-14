package ru.proj.sharedubki.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.proj.sharedubki.enums.AdvertType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adverts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "corpus")
    private int corpus;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "advert")
    private List<Image> images = new ArrayList<>();

    @Column(name = "preview_image_id")
    private Long previewImageId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @PrePersist
    private void init() {
        createdDate = LocalDateTime.now();
    }

    public void addImageToAdvert(Image image) {
        image.setAdvert(this);
        images.add(image);
    }

    public String getStringPrice() {
        String stringPrice = String.valueOf(price);
        return stringPrice;
    }

    public String getSimpleCategoryName() {
        AdvertType[] types = AdvertType.values();
        for (var e : types) {
            if (e.name().equals(this.category)) {
                return e.getTypeName();
            }
        }
        return null;
    }
}
