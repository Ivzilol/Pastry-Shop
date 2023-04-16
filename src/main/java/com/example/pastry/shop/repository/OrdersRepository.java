package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Set<Orders> findByUsers(Users user);

    @Query("select o from Orders as o" +
            " where o.status = 'confirmed'")
    Set<Orders> findByStatus();
}
