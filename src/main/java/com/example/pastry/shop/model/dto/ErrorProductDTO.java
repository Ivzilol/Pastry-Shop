package com.example.pastry.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorProductDTO {

    private String nameError;

    private String priceError;

    private String categoriesError;

    private String descriptionError;

    private String shopError;
}
