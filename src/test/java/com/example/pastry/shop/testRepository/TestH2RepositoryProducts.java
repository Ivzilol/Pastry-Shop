package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2RepositoryProducts extends JpaRepository<Products, Long> {
    
}
