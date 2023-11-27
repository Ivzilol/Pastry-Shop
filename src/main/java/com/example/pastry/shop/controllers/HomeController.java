package com.example.pastry.shop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/")
public interface HomeController {

    @GetMapping("")
    ResponseEntity<?> getMostOrderedProducts();
    @GetMapping("/home")
    ResponseEntity<?> getRecommendedProducts();
}
