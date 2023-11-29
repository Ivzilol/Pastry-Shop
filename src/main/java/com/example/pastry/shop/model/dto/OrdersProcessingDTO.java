package com.example.pastry.shop.model.dto;

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

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private boolean paid;

    public OrdersProcessingDTO() {
    }

    public OrdersProcessingDTO(Long id, Double totalPrice, String statusOrder, LocalDate dateOfReceipt, LocalDate dateOfDispatch, Long keyOrder, String username, String firstName, String lastName, String address) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.statusOrder = statusOrder;
        this.dateOfReceipt = dateOfReceipt;
        this.dateOfDispatch = dateOfDispatch;
        this.keyOrder = keyOrder;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public OrdersProcessingDTO(Long id, Double totalPrice, String statusOrder, LocalDate dateOfReceipt, LocalDate dateOfDispatch, Long keyOrder, String username, String firstName, String lastName, String address, boolean paid) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.statusOrder = statusOrder;
        this.dateOfReceipt = dateOfReceipt;
        this.dateOfDispatch = dateOfDispatch;
        this.keyOrder = keyOrder;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.paid = paid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
