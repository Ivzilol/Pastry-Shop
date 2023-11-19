package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders-processing")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Orders Processing")
public class OrdersProcessingController {

    private final OrderService orderService;

    public OrdersProcessingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<?> startProcessingOrder(@PathVariable Long id) {
        Set<Orders> currentOrders = this.orderService.findByUsersId(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Successful start processing order");
        return ResponseEntity.ok(customResponse);
    }
}
