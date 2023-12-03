package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface OrdersProcessingRepository extends JpaRepository<OrdersProcessing, Long> {


    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, u.username, u.firstName, u.lastName, u.address, op.paid)" +
            " from OrdersProcessing op" +
            " join Users as u on op.user.id = u.id" +
            " where op.statusOrder = :status_send")
    Set<OrdersProcessingDTO> findByStatus(@Param("status_send") String status_send);

    Set<OrdersProcessing> findOrderById(Long id);

    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, u.username, u.firstName, u.lastName, u.address)" +
            " from OrdersProcessing op" +
            " join Users as u on op.user.id = u.id" +
            " where op.user.id = :id" +
            " order by op.dateOfReceipt")
    Set<OrdersProcessingDTO> findByUserIdOrderByDateOfReceipt(Long id);
    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, u.username, u.firstName, u.lastName, u.address)" +
            " from OrdersProcessing op" +
            " join Users as u on op.user.id = u.id" +
            " order by op.dateOfDispatch")
    Set<OrdersProcessingDTO> findAllOrders();

    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, u.username, u.firstName, u.lastName, u.address)" +
            " from OrdersProcessing op" +
            " join Users as u on op.user.id = u.id" +
            " where op.dateOfReceipt between :startDate and :endDate" +
            " order by op.dateOfDispatch desc")
    Set<OrdersProcessingDTO> findByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, u.username, u.firstName, u.lastName, u.address)" +
            " from OrdersProcessing op" +
            " join Users as u on op.user.id = u.id" +
            " where u.username = :currentUser" +
            " order by op.dateOfDispatch desc")
    Set<OrdersProcessingDTO> findByUser(String currentUser);
}
