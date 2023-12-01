package com.example.pastry.shop.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RequestMapping("/api/orders-processing")
public interface OrdersProcessingController {

    @PostMapping("/admin/{id}")
    ResponseEntity<?> startProcessingOrder(@PathVariable Long id);

    @GetMapping("/admin/date")
    ResponseEntity<?> getOrdersByDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                      @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);
}
