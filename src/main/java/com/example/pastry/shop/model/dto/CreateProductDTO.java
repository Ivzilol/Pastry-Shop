package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueProductName;
import com.unboundid.util.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.example.pastry.shop.common.ExceptionMessages.*;

@Getter
@Setter
public class CreateProductDTO {

    @NotBlank(message = EMPTY_PRODUCT)
    @UniqueProductName
    private String name;

    @Positive(message = NEGATIVE_NUMBER_PRICE)
    @NotBlank(message = EMPTY_PRICE)
    private Double price;

    @NotBlank(message = EMPTY_CATEGORY)
    private String Categories;

    @NotBlank(message = EMPTY_DESCRIPTION)
    private String description;

    @NotNull
    private MultipartFile imageUrl;

    @NotBlank(message = EMPTY_SHOP_NAME)
    private String shopName;

    public CreateProductDTO() {
    }
}
