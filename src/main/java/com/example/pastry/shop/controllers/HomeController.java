package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "true")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getMostOrderedProducts() {
        List<Products> mostOrderedProducts = this.homeService.findMostOrderedProducts();
        return ResponseEntity.ok(mostOrderedProducts);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getRecommendedProducts() {
        List<Products> recommendedProducts = this.homeService.findRecommendedProducts();
        return ResponseEntity.ok(recommendedProducts);
    }

//    @GetMapping("/api/products/{id}")
//    public ResponseEntity<?> getCurrentProduct(@PathVariable Long id) {
//        Products product = this.homeService.findCurrentProduct(id);
//        return ResponseEntity.ok(product);
//    }
 }
