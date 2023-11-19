package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueProductName;
import com.unboundid.util.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class CreateProductDTO {

    @NotNull
    @UniqueProductName
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String Categories;
    @NotNull
    private String description;
    @NotNull
    private MultipartFile imageUrl;
    @NotNull
    private String shopName;

    public CreateProductDTO() {
    }
}
