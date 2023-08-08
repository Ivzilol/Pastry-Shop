package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TestH2RepositoryProducts extends JpaRepository<Products, Long> {
    Products findProductById(Long productId);

    @Query("select p from Products as p" +
            " where p.categories = 'pie'")
    Set<Products> findAllPies();
}
