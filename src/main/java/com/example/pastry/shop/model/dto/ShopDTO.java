package com.example.pastry.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {

    private Long id;

    private String town;

    private String address;

    private Integer number;

    private String status;

    public ShopDTO(Long id, String town, String address, Integer number, String status) {
        this.id = id;
        this.town = town;
        this.address = address;
        this.number = number;
        this.status = status;
    }
}
