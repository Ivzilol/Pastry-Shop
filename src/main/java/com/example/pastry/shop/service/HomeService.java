package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface HomeService {

    List<MostOrderedProductsDTO> findMostOrderedProducts();

    List<MostOrderedProductsDTO> findRecommendedProducts();

    List<MostOrderedProductsDTO> getProducts(List<MostOrderedProductsDTO> recommendedProducts);
}
