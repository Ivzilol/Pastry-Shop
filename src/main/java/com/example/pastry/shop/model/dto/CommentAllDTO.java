package com.example.pastry.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAllDTO {

    private String createdBy;

    private Long id;

    private String text;

    public CommentAllDTO(String createdBy, Long id, String text) {
        this.createdBy = createdBy;
        this.id = id;
        this.text = text;
    }

    public CommentAllDTO() {
    }
}
