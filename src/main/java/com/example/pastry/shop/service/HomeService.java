package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.repository.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final ProductRepository productRepository;

    public HomeService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> findMostOrderedProducts() {
        List<Products> mostOrderedProducts = this.productRepository.findMostOrderedProducts();
//        mostOrderedProducts.forEach(p -> p.getShops().setUsers(null));
        return getProducts(mostOrderedProducts);
    }

    public List<Products> findRecommendedProducts() {
        List<Products> recommendedProducts = this.productRepository.recommendedProducts();
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
