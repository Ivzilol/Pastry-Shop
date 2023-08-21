package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {


    @Query("select o from Orders as o" +
            " join Users as u on o.users.id = u.id" +
            " where o.status = 'newOrder'")
    Set<Orders> findByUsers(Users user);

    @Query("select o from Orders as o" +
            " where o.status = 'confirmed'")
    Set<Orders> findByStatus();

    @Query("select o from Orders as o")
    Set<Orders> findAllOrders();

    Set<Orders> findByKeyOrderProduct(Long id);

    @Query("select o from Orders as o" +
            " where o.users.id = :id")
    Set<Orders> findConfirmedOrder(Long id);

    @Query("select o from Orders as o" +
            " where o.users.id = :id")
    Set<Orders> findNotDeliveredOrders(Long id);

    @Query("select o from Orders as o" +
            " where o.status = 'confirmed' or o.status = 'sent'")
    Set<Orders> findAllNotSendOrders();
}
