package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    private final ProductRepository productRepository;

    public HomeService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> findMostOrderedProducts() {
        List<Products> mostOrderedProducts = this.productRepository.findMostOrderedProducts();
        List<Products> mostFourOrderedProducts = new ArrayList<>();
        mostFourOrderedProducts.add(mostOrderedProducts.get(0));
        mostFourOrderedProducts.add(mostOrderedProducts.get(1));
        mostFourOrderedProducts.add(mostOrderedProducts.get(2));
        mostFourOrderedProducts.add(mostOrderedProducts.get(3));
        return mostFourOrderedProducts;
    }

    public List<Products> findRecommendedProducts() {
        List<Products> recommendedProducts = this.productRepository.recommendedProducts();
        List<Products> fourRecommendedProducts = new ArrayList<>();
        fourRecommendedProducts.add(recommendedProducts.get(0));
        fourRecommendedProducts.add(recommendedProducts.get(1));
        fourRecommendedProducts.add(recommendedProducts.get(2));
        fourRecommendedProducts.add(recommendedProducts.get(3));
        return fourRecommendedProducts;
    }
}
