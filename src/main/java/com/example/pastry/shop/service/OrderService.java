package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.repository.OrdersRepository;

import java.time.LocalDate;

public class OrderService {

    private OrdersRepository ordersRepository;

    public OrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Iterable<Orders> getAllOrders() {
        return this.ordersRepository.findAll();
    }

    public Orders create(Orders order){
        order.setDateCreated(LocalDate.now());
        return this.ordersRepository.save(order);
    }

    public void update(Orders order) {
        this.ordersRepository.save(order);
    }
}
