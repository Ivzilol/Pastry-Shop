package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersProcessingRepository extends JpaRepository<OrdersProcessing, Long> {

    @Query("select o from OrdersProcessing as o" +
            " where o.statusOrder = 'sent'")
    Set<OrdersProcessing> findByStatus();

    Set<OrdersProcessing> findOrderById(Long id);

    Set<OrdersProcessing> findByUserIdOrderByDateOfReceipt(Long id);
    @Query("select op from OrdersProcessing AS op" +
            " order by op.dateOfDispatch")
    Set<OrdersProcessing> findAllOrders();
}
