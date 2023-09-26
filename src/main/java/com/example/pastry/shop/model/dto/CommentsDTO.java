package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Users;

public class CommentsDTO {

    private Long id;

    private String text;

    private Users createdBy;

    public CommentsDTO() {
    }

    public CommentsDTO(Long id, String text, Users createdBy) {
        this.id = id;
        this.text = text;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }
}
