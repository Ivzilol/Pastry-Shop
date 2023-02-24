package com.example.pastry.shop.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ShopStatusEnum {
    WORKING("Working", 1),
    NON_WORKING("non-working", 2);

    private final String status;
    private final Integer step;

    ShopStatusEnum(String status, Integer step) {
        this.status = status;
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public Integer getStep() {
        return step;
    }
}
