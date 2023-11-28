package com.example.pastry.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/")
public interface MonitoringController {


    @GetMapping("/monitoring")
    double getProductSearchCount();
}
