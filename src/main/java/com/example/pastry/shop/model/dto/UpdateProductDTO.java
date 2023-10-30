package com.example.pastry.shop.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductDTO {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private String categories;

    @NotNull
    private String description;

}
