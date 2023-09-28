package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.OrdersDTO;
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


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            " o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct, o.users)" +
            " from Orders as o" +
            " where o.status = 'confirmed'")
    Set<OrdersDTO> findByStatus();

    @Query("select o from Orders as o")
    Set<Orders> findAllOrders();

    Set<Orders> findByKeyOrderProduct(Long id);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.users.id = :id and o.status = 'confirmed' or o.status = 'sent'")
    Set<OrdersDTO> findConfirmedOrder(Long id);

    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.users.id = :id and o.status = 'newOrder'")
    Set<OrdersDTO> findNotDeliveredOrders(Long id);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.status = 'confirmed' or o.status = 'sent'")
    Set<OrdersDTO> findAllNotSendOrders();


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            " o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.status = 'newOrder' and o.users.id = :id")
    Set<OrdersDTO> findByUsersId(Long id);
}
