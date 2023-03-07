package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Products product = productsService.createProduct(createProductDTO);
        return ResponseEntity.ok(product);
    }
}
