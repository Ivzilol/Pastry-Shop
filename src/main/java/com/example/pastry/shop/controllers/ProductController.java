package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Products product = productsService.createProduct(createProductDTO);
        return ResponseEntity.ok(product);
    }

    @GetMapping("")
    public ResponseEntity<?> getShops(@AuthenticationPrincipal Users user) {
        Set<Products> shopById = productsService.findByUser(user);
        return ResponseEntity.ok(shopById);
    }
}
