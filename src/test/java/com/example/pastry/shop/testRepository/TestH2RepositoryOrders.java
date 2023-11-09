package com.example.pastry.shop.testRepository;

import com.example.pastry.shop.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2RepositoryOrders extends JpaRepository<Orders, Long> {
}
