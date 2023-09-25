package com.example.pastry.shop.model.dto;

public class GetUserMessagesDTO {

    private Long id;

    private String message;

    private Long adminId;

    public GetUserMessagesDTO(Long id, String message, Long adminId) {
        this.id = id;
        this.message = message;
        this.adminId = adminId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
