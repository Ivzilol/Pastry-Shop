package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query("select p from Products AS p")
    Set<Products> findByAdmin(Users user);

    @Query("select p from Products AS p")
    Set<Products> findByUser(Users user);

    Optional<Products> findByName(String name);

    Products findProductById(Long id);
    @Query("select p from Products as p" +
            " order by p.numberOrders desc ")
    List<Products> findMostOrderedProducts();

    @Query("select p from Products as p" +
            " order by p.numberOrders asc ")
    List<Products> recommendedProducts();

    @Query("select p from Products as p" +
            " where p.categories = 'pie'")
    Set<Products> findAllPies();
    @Query("select p from Products as p" +
            " where p.categories = 'buns'")
    Set<Products> findAllBuns();
    @Query("select p from Products as p" +
            " where p.categories = 'sweets'")
    Set<Products> findAllSweets();
    @Query("select p from Products as p" +
            " where p.categories = 'cake'")
    Set<Products> findAllCakes();

    Set<Products> findByCategories(String category);
}
