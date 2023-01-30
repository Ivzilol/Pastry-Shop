package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @PostMapping("")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal Users user) {
        Products newProduct = productsService.createProduct(user);

        return ResponseEntity.ok(newProduct);
    }
 }
