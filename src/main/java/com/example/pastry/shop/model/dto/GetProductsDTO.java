package com.example.pastry.shop.model.dto;

import java.util.Set;

public class GetProductsDTO {

    private Long id;

    private String name;

    private Double price;

    private String description;

    private String imageUrl;

    private Set<Object> userLikes;

    public GetProductsDTO() {
    }

    public GetProductsDTO(Long id, String name, Double price, String description, String imageUrl, Set<Object> userLikes) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userLikes = userLikes;
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

    public Set<Object> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<Object> userLikes) {
        this.userLikes = userLikes;
    }
}
