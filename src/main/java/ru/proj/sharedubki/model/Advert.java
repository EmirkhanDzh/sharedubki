package ru.proj.sharedubki.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Advert {

    private Long id;
    private String type;
    private String title;
    private int price;
    private int corpus;
    // private String description;
    // private LocalDateTime createDate;


}
