package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.OrdersProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrdersProcessingRepository extends JpaRepository<OrdersProcessing, Long> {

    @Query("select o from OrdersProcessing as o" +
            " where o.statusOrder = 'sent'")
    Set<OrdersProcessing> findByStatus();
}
