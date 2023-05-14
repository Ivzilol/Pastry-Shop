package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.OrdersProcessingRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderProcessingService {

    private final OrdersProcessingRepository ordersProcessingRepository;

    public OrderProcessingService(OrdersProcessingRepository ordersProcessingRepository) {
        this.ordersProcessingRepository = ordersProcessingRepository;
    }


    public Set<OrdersProcessing> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersProcessingRepository.findByStatus();
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }


    public Set<OrdersProcessing> findOrdersCurrentUser(Users user) {
        return this.ordersProcessingRepository.findByUserIdOrderByDateOfReceipt(user.getId());
    }
}
