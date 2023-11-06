package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.ShopDTO;
import com.example.pastry.shop.model.dto.ShopsDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, Long> {

    Set<Shops> findByUsers(Users user);

    @Query("select s from Shops as s" +
            " where s.name = :name")
    Shops findByName(String name);

    @Query("select new com.example.pastry.shop.model.dto.ShopsDTO(" +
            " s.id, s.number, s.name, s.status, s.town, s.address as address)" +
            " from Shops as s")
    List<ShopsDTO> findAllShops();

    @Query("select new com.example.pastry.shop.model.dto.ShopDTO(" +
            "s.id, s.name, s.town, s.address, s.number, s.status) " +
            " from Shops as s")
    Optional<ShopDTO> findShopById(Long shopId);
}
