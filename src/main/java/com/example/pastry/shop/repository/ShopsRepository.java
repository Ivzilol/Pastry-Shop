package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, Long> {

    Set<Shops> findByUsers(Users user);

    Optional<Shops> findByName(String name);
}
