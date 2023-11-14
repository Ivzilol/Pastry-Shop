package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.OrdersProcessingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderProcessingService {

    private final OrdersProcessingRepository ordersProcessingRepository;

    @Value("${status_send}")
    private String statusSend;

    public OrderProcessingService(OrdersProcessingRepository ordersProcessingRepository) {
        this.ordersProcessingRepository = ordersProcessingRepository;
    }


    public Set<OrdersProcessingDTO> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersProcessingRepository.findByStatus(statusSend);
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }


    public Set<OrdersProcessingDTO> findOrdersCurrentUser(Users user) {
        return this.ordersProcessingRepository.findByUserIdOrderByDateOfReceipt(user.getId());
    }

    public Set<OrdersProcessingDTO> getAllOrders() {
        return this.ordersProcessingRepository.findAllOrders();
    }
}
