package com.example.pastry.shop.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders_processing")
public class OrdersProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
