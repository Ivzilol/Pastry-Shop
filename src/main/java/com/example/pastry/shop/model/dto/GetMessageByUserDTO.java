package com.example.pastry.shop.model.dto;

public class GetMessageByUserDTO {

    private String message;

    private Long userId;

    private Long adminId;

    public GetMessageByUserDTO(String message, Long userId, Long adminId) {
        this.message = message;
        this.userId = userId;
        this.adminId = adminId;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
