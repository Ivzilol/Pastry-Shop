package com.example.pastry.shop.model.dto;

public class OrdersStatusDTO {

    private String status;

    private String payment;

    public OrdersStatusDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
