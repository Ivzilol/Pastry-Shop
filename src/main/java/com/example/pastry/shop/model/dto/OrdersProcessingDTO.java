package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class OrdersProcessingDTO {

    private Long id;

    private Double totalPrice;

    private String statusOrder;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfReceipt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfDispatch;

    private Long keyOrder;

    private Users user;

    public OrdersProcessingDTO() {
    }

    public OrdersProcessingDTO(Long id, Double totalPrice, String statusOrder, LocalDate dateOfReceipt, LocalDate dateOfDispatch, Long keyOrder, Users user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.statusOrder = statusOrder;
        this.dateOfReceipt = dateOfReceipt;
        this.dateOfDispatch = dateOfDispatch;
        this.keyOrder = keyOrder;
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public LocalDate getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(LocalDate dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public LocalDate getDateOfDispatch() {
        return dateOfDispatch;
    }

    public void setDateOfDispatch(LocalDate dateOfDispatch) {
        this.dateOfDispatch = dateOfDispatch;
    }

    public Long getKeyOrder() {
        return keyOrder;
    }

    public void setKeyOrder(Long keyOrder) {
        this.keyOrder = keyOrder;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
