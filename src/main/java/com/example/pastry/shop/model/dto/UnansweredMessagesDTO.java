package com.example.pastry.shop.model.dto;

public class UnansweredMessagesDTO {

    private String message;

    private Long userId;

    public UnansweredMessagesDTO(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}