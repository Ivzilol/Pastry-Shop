package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.OrdersProcessingRepository;
import com.example.pastry.shop.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private final OrdersProcessingRepository ordersProcessingRepository;

    @Value("${status_send}")
    private String statusSend;


    public OrderProcessingServiceImpl(OrdersProcessingRepository ordersProcessingRepository) {
        this.ordersProcessingRepository = ordersProcessingRepository;
    }


    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    @Override
    public Set<OrdersProcessingDTO> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersProcessingRepository.findByStatus(statusSend);
        } else {
            return null;
        }
    }

    @Override
    public Set<OrdersProcessingDTO> findOrdersCurrentUser(Users user) {
        return this.ordersProcessingRepository.findByUserIdOrderByDateOfReceipt(user.getId());
    }

    @Override
    public Set<OrdersProcessingDTO> getAllOrders() {
        return this.ordersProcessingRepository.findAllOrders();
    }

    @Override
    public Set<OrdersProcessingDTO> findOrdersByDate(Users user ,LocalDate startDate, LocalDate endDate) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersProcessingRepository.findByDate(startDate, endDate);
        }
        return null;
    }

    public Set<OrdersProcessingDTO> findOrdersByUser(Users user, String currentUser) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersProcessingRepository.findByUser(currentUser);
        }
        return null;
    }
}
