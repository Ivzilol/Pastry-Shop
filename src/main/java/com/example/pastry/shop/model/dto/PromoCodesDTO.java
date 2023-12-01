package com.example.pastry.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoCodesDTO {

    private String promoCode;

    public PromoCodesDTO(String promoCode) {
        this.promoCode = promoCode;
    }
}
