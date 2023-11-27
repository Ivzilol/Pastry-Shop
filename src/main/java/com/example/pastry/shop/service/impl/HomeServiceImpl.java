package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final ProductRepository productRepository;

    public HomeServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<MostOrderedProductsDTO> findMostOrderedProducts() {
        List<MostOrderedProductsDTO> mostOrderedProductsDTO = this.productRepository.findMostOrderedProducts();
        return getProducts(mostOrderedProductsDTO);
    }

    @Override
    public List<MostOrderedProductsDTO> findRecommendedProducts() {
        List<MostOrderedProductsDTO> recommendedProducts = this.productRepository.recommendedProducts();
        return getProducts(recommendedProducts);
    }

    @Override
    public List<MostOrderedProductsDTO> getProducts(List<MostOrderedProductsDTO> recommendedProducts) {
        List<MostOrderedProductsDTO> fourRecommendedProducts = new ArrayList<>();
        fourRecommendedProducts.add(recommendedProducts.get(0));
        fourRecommendedProducts.add(recommendedProducts.get(1));
        fourRecommendedProducts.add(recommendedProducts.get(2));
        fourRecommendedProducts.add(recommendedProducts.get(3));
        return fourRecommendedProducts;
    }
}
