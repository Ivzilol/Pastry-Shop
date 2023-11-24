package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueProductName;
import com.unboundid.util.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class CreateProductDTO {

    @NotBlank(message = "Product name can not be empty!")
    @UniqueProductName(message = "Product with this name already exist")
    private String name;
    @NotBlank(message = "Product price can not be empty!")
    private Double price;
    @NotBlank(message = "Product category can not be empty!")
    private String Categories;
    @NotBlank(message = "Product description can not be empty!")
    private String description;
    @NotNull
    private MultipartFile imageUrl;
    @NotBlank(message = "Shop name can not be empty!")
    private String shopName;

    public CreateProductDTO() {
    }
}
