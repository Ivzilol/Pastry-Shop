package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersProcessingRepository extends JpaRepository<OrdersProcessing, Long> {

//    @Query("select o from OrdersProcessing as o" +
//            " where o.statusOrder = 'sent'")
//    Set<OrdersProcessing> findByStatus();

    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, op.user)" +
            " from OrdersProcessing op" +
            " where op.statusOrder = 'sent'")
    Set<OrdersProcessingDTO> findByStatus();

    Set<OrdersProcessing> findOrderById(Long id);

    @Query("select new com.example.pastry.shop.model.dto.OrdersProcessingDTO(" +
            " op.id, op.totalPrice, op.statusOrder, op.dateOfReceipt, " +
            " op.dateOfDispatch, op.keyOrder as keyOrder, op.user)" +
            " from OrdersProcessing op" +
            " where op.user.id = :id" +
            " order by op.dateOfReceipt")
    Set<OrdersProcessingDTO> findByUserIdOrderByDateOfReceipt(Long id);
    @Query("select op from OrdersProcessing AS op" +
            " order by op.dateOfDispatch")
    Set<OrdersProcessing> findAllOrders();
}
