package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    private final ProductRepository productRepository;

    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Products createProduct(Users user) {
        return null;
    }
}
