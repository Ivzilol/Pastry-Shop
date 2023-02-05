package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.enums.ShopsEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopResponseDTO {

    private Shops shops;

    private ShopsEnum[] shopsEnums = ShopsEnum.values();

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

    public ShopsEnum[] getShopsEnums() {
        return shopsEnums;
    }

    public void setShopsEnums(ShopsEnum[] shopsEnums) {
        this.shopsEnums = shopsEnums;
    }
}
