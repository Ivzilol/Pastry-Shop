package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.enums.ShopStatusEnum;
import com.example.pastry.shop.model.enums.ShopsEnum;

public class ShopResponseDTO {

    private Shops shops;

    private final ShopStatusEnum[] statusEnums = ShopStatusEnum.values();

    public ShopResponseDTO(Shops shops) {
        super();
        this.shops = shops;
    }

    public Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;
    }

    public void setShopsEnums(ShopsEnum[] shopsEnums) {
    }

    public ShopStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
