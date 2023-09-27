package com.example.pastry.shop.model.dto;

public class GetProductsDTO {

    private Long id;

    private String name;

    private Double price;

    private String categories;

    private String description;

    private String imageUrl;

    private Long shopId;



    public GetProductsDTO(Long id, String name, Double price, String categories, String description, String imageUrl, Long shopId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
        this.description = description;
        this.imageUrl = imageUrl;
        this.shopId = shopId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
