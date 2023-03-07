package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsService {

    private final ProductRepository productRepository;

    private final ShopsRepository shopsRepository;

    private ShopsService shopsService;

    public ProductsService(ProductRepository productRepository, ShopsRepository shopsRepository, ShopsService shopsService) {
        this.productRepository = productRepository;
        this.shopsRepository = shopsRepository;
        this.shopsService = shopsService;
    }

    public Products createProduct(CreateProductDTO productDTO) {
       Products newProduct = new Products();
       newProduct.setPrice(productDTO.getPrice());
       newProduct.setDescription(productDTO.getDescription());
       newProduct.setCategories(productDTO.getCategories());
       newProduct.setImageUrl(productDTO.getImageUrl());
       Optional<Shops> shop = this.shopsRepository.findByName(productDTO.getShopName());
       newProduct.setShops(shop.get());
       productRepository.save(newProduct);
        return newProduct;
    }
}
