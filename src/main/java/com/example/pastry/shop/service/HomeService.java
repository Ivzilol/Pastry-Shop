package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HomeService {

    private final ProductRepository productRepository;

    public HomeService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> findMostOrderedProducts() {
        List<Products> mostOrderedProducts = this.productRepository.findMostOrderedProducts();
        List<Products> mostTwoOrderedProducts = new ArrayList<>();
        mostTwoOrderedProducts.add(mostOrderedProducts.get(0));
        mostTwoOrderedProducts.add(mostOrderedProducts.get(1));
        return mostTwoOrderedProducts;
    }
}
