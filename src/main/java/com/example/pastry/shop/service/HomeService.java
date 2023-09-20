package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
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
        mostOrderedProducts.forEach(p -> p.setUserLikes(null));
        mostOrderedProducts.forEach(p -> p.getShops().setUsers(null));
        return getProducts(mostOrderedProducts);
    }

    public List<Products> findRecommendedProducts() {
        List<Products> recommendedProducts = this.productRepository.recommendedProducts();
        recommendedProducts.forEach(p -> p.setUserLikes(null));
        recommendedProducts.forEach(p -> p.getShops().setUsers(null));
        return getProducts(recommendedProducts);
    }

    @NotNull
    private List<Products> getProducts(List<Products> recommendedProducts) {
        List<Products> fourRecommendedProducts = new ArrayList<>();
        fourRecommendedProducts.add(recommendedProducts.get(0));
        fourRecommendedProducts.add(recommendedProducts.get(1));
        fourRecommendedProducts.add(recommendedProducts.get(2));
        fourRecommendedProducts.add(recommendedProducts.get(3));
        return fourRecommendedProducts;
    }
}
