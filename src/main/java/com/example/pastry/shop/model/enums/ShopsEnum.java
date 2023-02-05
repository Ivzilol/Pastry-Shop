package com.example.pastry.shop.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ShopsEnum {
    SHOP_1(1, "Sladkarnicata na Mama - Samokov"),
    SHOP_2(2, "Sladkarnicata na Mama - Sofia"),
    SHOP_3(3, "Sladkarnicata na Mama - Plovdiv"),
    SHOP_4(4, "Sladkarnicata na Mama - Varna");
    private final int shopNumber;
    private final String shopName;
    ShopsEnum(int shopNumber, String shopName) {
        this.shopNumber = shopNumber;
        this.shopName = shopName;
    }

    public int getShopNumber() {
        return shopNumber;
    }

    public String getShopName() {
        return shopName;
    }
}
