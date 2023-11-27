package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface OrderProcessingService {

    Set<OrdersProcessingDTO> findByStatus(Users user);

    Set<OrdersProcessingDTO> findOrdersCurrentUser(Users user);

    Set<OrdersProcessingDTO> getAllOrders();


}
