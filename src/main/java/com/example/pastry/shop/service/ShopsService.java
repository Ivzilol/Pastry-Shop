package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.ShopDTO;
import com.example.pastry.shop.model.dto.ShopsDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;

import java.util.List;
import java.util.Optional;

public interface ShopsService {

    void createShop(Users user);

    Optional<ShopDTO> findById(Long shopId);

    List<ShopsDTO> findAll();

    void deleteShop(Long shopId, Users user);

    Shops saveShop(Shops shop, Users user);

    boolean updateShop(Shops shop, Long shopId, Users user);

    Shops findByName(String name);
}
