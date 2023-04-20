package com.example.pastry.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "orders")
public class Orders {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private String status;

    private Double price;

    private String productName;

    @Column(name = "key_order_product", unique = true)
    private Long keyOrderProduct;
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
}
