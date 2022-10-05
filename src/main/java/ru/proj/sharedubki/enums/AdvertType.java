package ru.proj.sharedubki.enums;

import lombok.Data;

public enum AdvertType {
    PRODUCT_FOOD("products&food"),
    ELECTRONICS("electronics"),
    SERVICE("services"),
    WEAR("wear"),
    LOST_AND_FOUND("lost&found"),
    OTHER("other"),
    ;

    private final String typeName;

    public String getTypeName() {
        return typeName;
    }

    AdvertType(String typeName) {
        this.typeName = typeName;
    }


}
