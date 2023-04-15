package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.model.enums.PaymentMethod;
import com.example.pastry.shop.repository.OrdersRepository;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    private final UsersRepository usersRepository;

    private final ProductRepository productRepository;

    public OrderService(OrdersRepository ordersRepository, UsersRepository usersRepository, ProductRepository productRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
    }


    public Orders createOrder(Long id, Users user) {
        Orders newOrder = new Orders();
        newOrder.setDateCreated(LocalDate.now());
        Optional<Users> byUsername = this.usersRepository.findByUsername(user.getUsername());
        newOrder.setUsers(byUsername.get());
        Optional<Products> product = productRepository.findById(id);
        newOrder.setStatus("newOrder");
        newOrder.setPrice(product.get().getPrice());
        newOrder.setProductName(product.get().getName());
        ordersRepository.save(newOrder);
        return newOrder;
    }

    public Set<Orders> findByUser(Users user) {
        boolean isUser = user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
        if (isUser) {
            return ordersRepository.findByUsers(user);
        } else {
            return null;
        }
    }

    public void removeProduct(Long id) {
        Optional<Orders> byId = this.ordersRepository.findById(id);
        this.ordersRepository.delete(byId.get());
    }


    public Orders updateStatus(OrdersStatusDTO ordersStatusDTO, Users user) {
        Set<Orders> byUsers = this.ordersRepository.findByUsers(user);
        for (Orders currentOrder : byUsers) {
            currentOrder.setStatus(ordersStatusDTO.getStatus());
            this.ordersRepository.save(currentOrder);
        }
        return (Orders) byUsers;
    }
}
