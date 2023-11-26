package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.model.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.time.LocalDate;

public class OrdersDTO {

    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfDelivery;
    @JsonFormat(pattern = "HH:mm")
    private Time timeOfDelivery;

    private String status;

    private Double price;

    private String productName;

    private Long keyOrderProduct;

    private Users users;

    private Double totalPrice;


    public OrdersDTO(Long id, LocalDate dateCreated, LocalDate dateOfDelivery, Time timeOfDelivery, String status, Double price, String productName, Long keyOrderProduct, Users users) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateOfDelivery = dateOfDelivery;
        this.timeOfDelivery = timeOfDelivery;
        this.status = status;
        this.price = price;
        this.productName = productName;
        this.keyOrderProduct = keyOrderProduct;
        this.users = users;
    }

    public OrdersDTO(Long id, LocalDate dateCreated, LocalDate dateOfDelivery, Time timeOfDelivery, String status, Double price, String productName, Long keyOrderProduct) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateOfDelivery = dateOfDelivery;
        this.timeOfDelivery = timeOfDelivery;
        this.status = status;
        this.price = price;
        this.productName = productName;
        this.keyOrderProduct = keyOrderProduct;
    }

    public OrdersDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public Time getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(Time timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getKeyOrderProduct() {
        return keyOrderProduct;
    }

    public void setKeyOrderProduct(Long keyOrderProduct) {
        this.keyOrderProduct = keyOrderProduct;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
