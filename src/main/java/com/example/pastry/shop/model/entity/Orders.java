package com.example.pastry.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "key_order_product")
    private Long keyOrderProduct;

    @Column(columnDefinition = "boolean default false")
    private boolean paid;

    @ManyToOne
    private Users users;

    public Orders() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
