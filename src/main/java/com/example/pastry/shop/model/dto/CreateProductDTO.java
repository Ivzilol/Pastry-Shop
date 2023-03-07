package com.example.pastry.shop.model.dto;

import com.unboundid.util.NotNull;

import java.math.BigDecimal;

public class CreateProductDTO {
    @NotNull
    private BigDecimal price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
