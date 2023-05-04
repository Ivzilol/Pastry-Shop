package com.example.pastry.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class OrderStatusSendAdmin {

    private String status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDelivery;

    public OrderStatusSendAdmin() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(LocalDate dateDelivery) {
        this.dateDelivery = dateDelivery;
    }
}
