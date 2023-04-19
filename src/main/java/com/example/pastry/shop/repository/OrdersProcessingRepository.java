package com.example.pastry.shop.repository;

import com.example.pastry.shop.model.entity.OrdersProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersProcessingRepository extends JpaRepository<OrdersProcessing, Long> {

}
