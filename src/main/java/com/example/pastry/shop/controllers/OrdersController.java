package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.OrderDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {


    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createOrder(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {

    Orders createOrder = this.orderService.createOrder(id, user);
    return ResponseEntity.ok(createOrder);

    }
}
