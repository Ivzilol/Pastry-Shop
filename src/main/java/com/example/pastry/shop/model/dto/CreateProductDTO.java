package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueProductName;
import com.unboundid.util.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import static com.example.pastry.shop.common.ExceptionMessages.*;


public class CreateProductDTO {

    @NotBlank(message = EMPTY_PRODUCT)
    @UniqueProductName
    private String name;

    @Positive(message = NEGATIVE_NUMBER_PRICE)
    @NotNull
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategories() {
        return Categories;
    }

    public void setCategories(String categories) {
        Categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
