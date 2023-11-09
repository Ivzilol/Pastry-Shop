package com.example.pastry.shop.model.dto;

public class OrderStatusSendAdmin {

    private String status;

    private String dateDelivery;

    private String timeDelivery;

    public OrderStatusSendAdmin() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public String getTimeDelivery() {
        return timeDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public void setTimeDelivery(String timeDelivery) {
        this.timeDelivery = timeDelivery;
    }
}
