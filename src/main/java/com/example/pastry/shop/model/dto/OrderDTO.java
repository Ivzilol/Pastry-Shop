package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.PaymentMethod;

import java.time.LocalDate;

public class OrderDTO {


    private Double price;

    private LocalDate dataCreated;

    private String status;

    private Users user;

    private PaymentMethod method;

    public OrderDTO() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDataCreated(LocalDate dataCreated) {
        this.dataCreated = dataCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
