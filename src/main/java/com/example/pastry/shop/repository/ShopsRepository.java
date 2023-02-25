package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.ShopStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, Long> {

    Set<Shops> findByUsers(Users user);

    @Query("select s from Shops as s " +
            "where s.status = 'non-working'" )
    Set<Shops> findByModerator(Users user);
}
