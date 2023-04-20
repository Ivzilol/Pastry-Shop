package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueProductName;
import com.unboundid.util.NotNull;

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
    private String imageUrl;
    @NotNull
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
