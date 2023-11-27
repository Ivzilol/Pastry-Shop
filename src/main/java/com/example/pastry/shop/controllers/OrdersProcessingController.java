package com.example.pastry.shop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/orders-processing")
public interface OrdersProcessingController {

    @PostMapping("/admin/{id}")
    ResponseEntity<?> startProcessingOrder(@PathVariable Long id);
}
