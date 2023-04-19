package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.OrdersProcessingRepository;
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

    private final OrdersProcessingRepository ordersProcessingRepository;

    private int keyProductOrder = 0;

    public OrderService(OrdersRepository ordersRepository, UsersRepository usersRepository, ProductRepository productRepository, OrdersProcessingRepository ordersProcessingRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
        this.ordersProcessingRepository = ordersProcessingRepository;
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
        boolean isUser = isUser(user);
        if (isUser) {
            return ordersRepository.findByUsers(user);
        } else {
            return null;
        }
    }

    private static boolean isUser(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
    }

    public void removeProduct(Long id) {
        Optional<Orders> byId = this.ordersRepository.findById(id);
        this.ordersRepository.delete(byId.get());
    }


    public Orders updateStatus(OrdersStatusDTO ordersStatusDTO, Users user) {
        Set<Orders> byUsers = this.ordersRepository.findByUsers(user);
        for (Orders currentOrder : byUsers) {
            currentOrder.setStatus(ordersStatusDTO.getStatus());
            currentOrder.setKeyOrderProduct(keyProductOrder + 1);
            this.ordersRepository.save(currentOrder);
        }
        this.keyProductOrder += 1;
        return (Orders) byUsers;
    }

    public Set<Orders> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.ordersRepository.findByStatus();
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    public Set<Orders> findByUsersId(Long id) {
        OrdersProcessing ordersProcessing = new OrdersProcessing();
        Set<Orders> byUsers_id = this.ordersRepository.findByUsers_Id(id);
        double totalPrice = 0;
        for (Orders currentOrder : byUsers_id) {
            totalPrice += currentOrder.getPrice();
        }
        ordersProcessing.setTotalPrice(totalPrice);
        Optional<Users> user = this.usersRepository.findById(id);
        ordersProcessing.setUser(user.get());
        ordersProcessing.setStatusOrder("sent");
        ordersProcessing.setDateOfDispatch(LocalDate.now());
        Set<Orders> keyOrdersAll = this.ordersRepository.findByUsers_Id(id);
        int keyOrders = 0;
        for (Orders currentOrder : keyOrdersAll) {
            keyOrders = currentOrder.getKeyOrderProduct();
        }
        ordersProcessing.setKeyOrder(keyOrders);
        this.ordersProcessingRepository.save(ordersProcessing);
        return byUsers_id;
    }
}
