package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersDTO;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Set;

public interface OrderService {

    Orders createOrder(Long id, Users user);

    Set<OrdersDTO> findByUser(Users user);

    void removeProduct(Long id);

    boolean updateStatus(OrdersStatusDTO ordersStatusDTO, Users user);

    Set<OrdersDTO> findByStatus(Users user);

    boolean findByUsersId(Long id);

    Set<Orders> getOrdersByKey(Long id);

    boolean updateStatusSend(OrderStatusSendAdmin orderStatusSendAdmin, Long id) throws ParseException;

    void updateStatusDelivery(OrderStatusDeliveryAdmin orderStatusDeliveryAdmin, Long id);

    Set<OrdersDTO> trackingByStatus(Users user);

    Set<OrdersDTO> findOrdersWhichNotDelivered(Users user);

    Set<OrdersDTO> findOrdersWhichConfirmed(Users user);

    Set<OrdersDTO> findAllNotSendOrders();
}
