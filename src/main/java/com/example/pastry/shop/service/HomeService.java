package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
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


    public List<MostOrderedProductsDTO> findMostOrderedProducts() {
        List<MostOrderedProductsDTO> mostOrderedProductsDTO = this.productRepository.findMostOrderedProducts();
        return getProducts(mostOrderedProductsDTO);
    }

    public List<MostOrderedProductsDTO> findRecommendedProducts() {
        List<MostOrderedProductsDTO> recommendedProducts = this.productRepository.recommendedProducts();
        return getProducts(recommendedProducts);
    }

    @NotNull
    private List<MostOrderedProductsDTO> getProducts(List<MostOrderedProductsDTO> recommendedProducts) {
        List<MostOrderedProductsDTO> fourRecommendedProducts = new ArrayList<>();
        fourRecommendedProducts.add(recommendedProducts.get(0));
        fourRecommendedProducts.add(recommendedProducts.get(1));
        fourRecommendedProducts.add(recommendedProducts.get(2));
        fourRecommendedProducts.add(recommendedProducts.get(3));
        return fourRecommendedProducts;
    }
}
