package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.Users;

import java.time.LocalDate;
import java.util.Set;


public interface OrderProcessingService {

    Set<OrdersProcessingDTO> findByStatus(Users user);

    Set<OrdersProcessingDTO> findOrdersCurrentUser(Users user);

    Set<OrdersProcessingDTO> getAllOrders();

    Set<OrdersProcessingDTO> findOrdersByDate(Users user ,LocalDate startDate, LocalDate endDate);
}
