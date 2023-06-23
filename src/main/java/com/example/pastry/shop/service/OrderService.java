package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrdersRepository ordersRepository;

    private final UsersRepository usersRepository;

    private final ProductRepository productRepository;

    private final OrdersProcessingRepository ordersProcessingRepository;

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
        Products productIncreaseNumberOrders = this.productRepository.findProductById(id);
        productIncreaseNumberOrders.setNumberOrders(productIncreaseNumberOrders.getNumberOrders() + 1);
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
        Set<Orders> lastKey = this.ordersRepository.findAllOrders();
        Long mostBigKey = 0L;
        for (Orders order : lastKey) {
            if (order.getKeyOrderProduct() == null) {
                continue;
            }
            if (order.getKeyOrderProduct() > mostBigKey) {
                mostBigKey = order.getKeyOrderProduct();
            }
        }
        for (Orders currentOrder : byUsers) {
            currentOrder.setStatus(ordersStatusDTO.getStatus());
            currentOrder.setKeyOrderProduct(mostBigKey + 1);
            this.ordersRepository.save(currentOrder);
        }
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
        Set<Orders> byOrderKey = this.ordersRepository.findByKeyOrderProduct(id);
        double totalPrice = byOrderKey.stream().mapToDouble(Orders::getPrice).sum();
        ordersProcessing.setTotalPrice(totalPrice);
        Optional<Users> currentUser = this.usersRepository.findUserBayKey(id);
        ordersProcessing.setUser(currentUser.get());
        ordersProcessing.setStatusOrder("sent");
        ordersProcessing.setDateOfDispatch(LocalDate.now());
        ordersProcessing.setKeyOrder(id);
        this.ordersProcessingRepository.save(ordersProcessing);
        return byOrderKey;
    }


    public Orders updateStatusSend(OrderStatusSendAdmin orderStatusSendAdmin, Long id) throws ParseException {
        Set<Orders> orders = this.ordersRepository.findByKeyOrderProduct(id);
        LocalDate localDate = LocalDate.parse(orderStatusSendAdmin.getDateDelivery());
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        java.sql.Time timeDelivery = new java.sql.Time(formatter
                .parse(String.valueOf(orderStatusSendAdmin.getTimeDelivery())).getTime());
        setStatusTimeAndDate(orderStatusSendAdmin, orders, localDate, timeDelivery);
        return (Orders) orders;
    }

    private void setStatusTimeAndDate(OrderStatusSendAdmin orderStatusSendAdmin, Set<Orders> orders, LocalDate localDate, Time timeDelivery) {
        for (Orders currentOrder : orders) {
            currentOrder.setStatus(orderStatusSendAdmin.getStatus());
            currentOrder.setDateOfDelivery(localDate);
            currentOrder.setTimeOfDelivery(timeDelivery);
            this.ordersRepository.save(currentOrder);
        }
    }


    public OrdersProcessing updateStatusDelivery(OrderStatusDeliveryAdmin orderStatusDeliveryAdmin, Long id) {
        Set<OrdersProcessing> orders = this.ordersProcessingRepository.findOrderById(id);
        getStatus(orderStatusDeliveryAdmin, orders);
        Long keyOrder = getKeyOrder(orders);
        Set<Orders> ordersForChangeStatus = this.ordersRepository.findByKeyOrderProduct(keyOrder);
        setStatusAndDate(orders, ordersForChangeStatus);
        return (OrdersProcessing) orders;
    }

    private void setStatusAndDate(Set<OrdersProcessing> orders, Set<Orders> ordersForChangeStatus) {
        LocalDate dateRecipe = null;
        for (Orders currentOrder : ordersForChangeStatus) {
            currentOrder.setStatus("delivery");
            this.ordersRepository.save(currentOrder);
            dateRecipe = currentOrder.getDateOfDelivery();
        }
        for (OrdersProcessing order : orders) {
            order.setDateOfReceipt(dateRecipe);
            this.ordersProcessingRepository.save(order);
        }
    }

    private static Long getKeyOrder(Set<OrdersProcessing> orders) {
        Long keyOrder = 0L;
        for (OrdersProcessing order : orders) {
            keyOrder = order.getKeyOrder();
            break;
        }
        return keyOrder;
    }

    private void getStatus(OrderStatusDeliveryAdmin orderStatusDeliveryAdmin, Set<OrdersProcessing> orders) {
        for (OrdersProcessing currentOrder : orders) {
            currentOrder.setStatusOrder(orderStatusDeliveryAdmin.getStatus());
            this.ordersProcessingRepository.save(currentOrder);
        }
    }


    public Set<Orders> trackingByStatus(Users user) {
        Long id = user.getId();
        return this.ordersRepository.findConfirmedOrder(id);
    }
}
