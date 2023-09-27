package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.GetProductsDTO;
import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
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

    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = 'pie'")
    Set<GetProductsDTO> findAllPies();
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = 'buns'")
    Set<GetProductsDTO> findAllBuns();
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = 'sweets'")
    Set<GetProductsDTO> findAllSweets();
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = 'cake'")
    Set<GetProductsDTO> findAllCakes();

    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = :category")
    Set<GetProductsDTO> findByCategories(String category);

    @Query("select new com.example.pastry.shop.model.dto.MostOrderedProductsDTO(" +
            " p.id, p.name, p.imageUrl, p.price, p.description as description)" +
            " from Products as p" +
            " order by p.numberOrders desc")
    List<MostOrderedProductsDTO> findMostOrderedProducts();
    @Query("select new com.example.pastry.shop.model.dto.MostOrderedProductsDTO(" +
            " p.id, p.name, p.imageUrl, p.price, p.description as description)" +
            " from Products as p" +
            " order by p.numberOrders asc")
    List<MostOrderedProductsDTO> recommendedProducts();

    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.id = :productId")
    Optional<GetProductsDTO> findOrderProductById(Long productId);
}
