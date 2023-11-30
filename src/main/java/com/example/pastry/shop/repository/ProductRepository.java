package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.GetProductsDTO;
import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
import com.example.pastry.shop.model.dto.ProductsDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.PromoCodes;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query("select p from Products AS p")
    Set<Products> findByAdmin(Users user);

    @Query("select p from Products AS p" +
            " order by p.id")
    Set<Products> findByUser(Users user);

    @Query("select p from Products as p" +
            " where p.name = :name")
    Optional<Products> findByName(String name);

    Products findProductById(Long id);

    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = :category_p")
    Set<GetProductsDTO> findAllPies(@Param("category_p") String category_p);
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = :category_b")
    Set<GetProductsDTO> findAllBuns(@Param("category_b") String category_b);
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = :category_s")
    Set<GetProductsDTO> findAllSweets(@Param("category_s") String category_s);
    @Query("select new com.example.pastry.shop.model.dto.GetProductsDTO(" +
            " p.id, p.name, p.price, p.categories, p.description, p.imageUrl as imageUrl, p.shops.id)" +
            " from Products as p" +
            " where p.categories = :category_c")
    Set<GetProductsDTO> findAllCakes(@Param("category_c") String category_c);

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

    @Query("select pc from PromoCodes as pc" +
            " where pc.promoCode = :promoCode and pc.user.id = :userId and pc.isUsed = false")
    PromoCodes findPromoCodeByUser(String promoCode, Long userId);
}
