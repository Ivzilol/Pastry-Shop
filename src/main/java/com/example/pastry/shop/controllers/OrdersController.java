package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.OrdersRepository;
import com.example.pastry.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {


    private final OrderService orderService;
    private final OrdersRepository ordersRepository;

    public OrdersController(OrderService orderService, OrdersRepository ordersRepository) {
        this.orderService = orderService;
        this.ordersRepository = ordersRepository;
    }



    @PostMapping("/{id}")
    public ResponseEntity<?> createOrder(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {

        Orders createOrder = this.orderService.createOrder(id, user);
        return ResponseEntity.ok(createOrder);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal Users user) {
        Set<Orders> ordersById = orderService.findByUser(user);
        return ResponseEntity.ok(ordersById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductFromOrders(@PathVariable Long id) {
        this.orderService.removeProduct(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }


}
