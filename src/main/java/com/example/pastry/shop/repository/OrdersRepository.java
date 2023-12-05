package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.OrdersDTO;
import com.example.pastry.shop.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {


    @Query("select o from Orders as o" +
            " join Users as u on o.users.id = u.id" +
            " where o.status = :new_order and u.id = :userId")
    Set<Orders> findByUsers(Long userId, @Param("new_order") String new_order);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            " o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct, o.users)" +
            " from Orders as o" +
            " where o.status = :status_conf")
    Set<OrdersDTO> findByStatus(@Param("status_conf") String status_conf);

    @Query("select o from Orders as o")
    Set<Orders> findAllOrders();

    @Query("select o from Orders as o" +
            " where o.keyOrderProduct = :id")
    Set<Orders> findByKeyOrderProduct(Long id);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.users.id = :id and (o.status = :status_conf or o.status = :status_send)")
    Set<OrdersDTO> findConfirmedOrder(Long id, @Param("status_conf") String status_conf, @Param("status_send") String status_send);

    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.users.id = :id and o.status = :new_order")
    Set<OrdersDTO> findNotDeliveredOrders(Long id, @Param("new_order") String new_order);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            "o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.status = :status_conf or o.status = :status_send")
    Set<OrdersDTO> findAllNotSendOrders(@Param("status_conf") String status_conf, @Param("status_send") String status_send);


    @Query("select new com.example.pastry.shop.model.dto.OrdersDTO(" +
            " o.id, o.dateCreated, o.dateOfDelivery, o.timeOfDelivery, o.status, o.price, o.productName, o.keyOrderProduct as keyOrderProduct)" +
            " from Orders as o" +
            " where o.status = :new_order and o.users.id = :id")
    Set<OrdersDTO> findByUsersId(Long id, @Param("new_order") String new_order);


}
