package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query("select p from Products AS p")
    Set<Products> findByAdmin(Users user);

    @Query("select p from Products AS p")
    Set<Products> findByUser(Users user);

    Optional<Products> findByName(String name);
}
