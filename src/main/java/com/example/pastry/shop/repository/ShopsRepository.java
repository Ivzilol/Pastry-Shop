package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.AllShopsDTO;
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

    Optional<Shops> findByName(String name);

    @Query("select new com.example.pastry.shop.model.dto.AllShopsDTO(" +
            " s.id, s.number, s.name, s.status, s.town, s.address as address)" +
            " from Shops as s")
    List<AllShopsDTO> findAllShops();
}
