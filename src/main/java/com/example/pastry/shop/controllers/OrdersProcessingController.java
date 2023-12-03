package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Users;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api/orders-processing")
public interface OrdersProcessingController {

    @PostMapping("/admin/{id}")
    ResponseEntity<?> startProcessingOrder(@PathVariable Long id);

    @GetMapping("/admin/date")
    ResponseEntity<?> getOrdersByDate(@AuthenticationPrincipal Users user,
                                      @RequestParam("startDate")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                      @RequestParam("endDate")
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);

    @GetMapping("/admin/user")
    ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal Users user,
                                      @RequestParam("currentUser") String currentUser);
}
