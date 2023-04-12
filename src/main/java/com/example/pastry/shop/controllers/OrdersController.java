package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.OrderProduct;
import com.example.pastry.shop.model.entity.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {


    @PostMapping("/{id}")
    public ResponseEntity<?> createOrder(@PathVariable Long id) {
        List<OrderProduct> formDto;

        return ResponseEntity.ok(id);
    }
}
