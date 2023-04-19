package com.example.pastry.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders_processing")
public class OrdersProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price",nullable = false)
    private Double totalPrice;

    @Column(name = "status_order", nullable = false)
    private String statusOrder;

    @Column(name = "date_of_receipt")
    @JsonFormat (pattern = "dd/MM/yyyy")
    private LocalDate dateOfReceipt;
    @Column(nullable = false, name = "date_of_dispatch")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfDispatch;

    @Column(name = "key_order")
    private int keyOrder;

    @ManyToOne
    private Users user;


    public OrdersProcessing() {
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getKeyOrder() {
        return keyOrder;
    }

    public void setKeyOrder(int keyOrder) {
        this.keyOrder = keyOrder;
    }
}
