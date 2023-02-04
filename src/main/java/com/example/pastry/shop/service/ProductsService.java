package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.enums.Categories;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

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

    public Products createProduct(Shops shop) {
        Products product = new Products();
        product.setCategories(Categories.pies);
        product.setDescription("Vkusna banic");
        product.setImageUrl("https://gradcontent.com/lib/400x296/nai-balgarska-banica.JPG");
        product.setPrice(new BigDecimal("20.00"));
        Optional<Shops> shopId = shopsRepository.findById(product.getId());
        return productRepository.save(product);
    }
}
