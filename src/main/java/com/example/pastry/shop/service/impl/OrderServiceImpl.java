package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.events.UserTopClientEvent;
import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.*;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.*;
import com.example.pastry.shop.service.OrderService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final UsersRepository usersRepository;

    private final ProductRepository productRepository;

    private final OrdersProcessingRepository ordersProcessingRepository;

    private final PromoCodesRepository promoCodesRepository;

    private final ApplicationEventPublisher appEventPublisher;

    @Value("${status_confirmed}")
    private String statusConf;

    @Value("${status_new_order}")
    private String statusNewOrder;

    @Value("${status_send}")
    private String statusSend;

    @Value("${status_delivery}")
    private String statusDelivery;

    private Set<Orders> ordersInProcessing;

    private boolean isCodeValid = false;


    public OrderServiceImpl(OrdersRepository ordersRepository, UsersRepository usersRepository, ProductRepository productRepository, OrdersProcessingRepository ordersProcessingRepository, PromoCodesRepository promoCodesRepository, ApplicationEventPublisher appEventPublisher, Set<Orders> orders) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
        this.ordersProcessingRepository = ordersProcessingRepository;
        this.promoCodesRepository = promoCodesRepository;
        this.appEventPublisher = appEventPublisher;
        this.ordersInProcessing = orders;
    }

    @Override
    public Orders createOrder(Long id, Users user) {
        Orders newOrder = new Orders();
        newOrder.setDateCreated(LocalDate.now());
        Optional<Users> byUsername = this.usersRepository.findByUsername(user.getUsername());
        newOrder.setUsers(byUsername.get());
        Optional<Products> product = productRepository.findById(id);
        Products productIncreaseNumberOrders = this.productRepository.findProductById(id);
        productIncreaseNumberOrders.setNumberOrders(productIncreaseNumberOrders.getNumberOrders() + 1);
        newOrder.setStatus(statusNewOrder);
        double price = product.get().getPrice();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour = currentDateTime.format(formatter);
        int intHour = Integer.parseInt(hour);
        if (intHour >= 8 && intHour < 21) {
            price = price - price * 0.20;
        }
        newOrder.setPrice(price);
        newOrder.setProductName(product.get().getName());
        ordersRepository.save(newOrder);
        productRepository.save(productIncreaseNumberOrders);
        return newOrder;
    }

    @Override
    public Set<OrdersDTO> findByUser(Users user) {
        boolean isUser = isUser(user);
        if (isUser) {
            return ordersRepository.findByUsersId(user.getId(), statusNewOrder);
        } else {
            return null;
        }
    }

    @Override
    public void removeProduct(Long id) {
        Optional<Orders> byId = this.ordersRepository.findById(id);
        this.ordersRepository.delete(byId.get());
    }

    private static boolean isUser(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
    }


    @Override
    public boolean updateStatus(OrdersStatusDTO ordersStatusDTO, Users user) {
        isCodeValid = false;
        if (!Objects.equals(ordersStatusDTO.getPromoCode(), null)) {
            PromoCodes promoCode = validateCode(ordersStatusDTO.getPromoCode(), user);
            isCodeValid = promoCode != null;
            if (isCodeValid) {
                promoCode.setUsed(true);
                this.promoCodesRepository.save(promoCode);
                confirmOrder(ordersStatusDTO, user);
                return true;
            } else {
                return false;
            }
        } else {
            confirmOrder(ordersStatusDTO, user);
            return true;
        }
    }

    private void confirmOrder(OrdersStatusDTO ordersStatusDTO, Users user) {
        Set<Orders> byUsers = this.ordersRepository.findByUsers(user.getId(), statusNewOrder);
        Set<Orders> lastKey = this.ordersRepository.findAllOrders();
        Long mostBigKey = getKey(lastKey);
        setStatusAndKey(ordersStatusDTO, byUsers, mostBigKey);
        double allPrice = byUsers.stream().mapToDouble(Orders::getPrice).sum();
        if (allPrice >= 100) {
            appEventPublisher.publishEvent(new UserTopClientEvent(
                    "userTopClient", user.getEmail()
            ));
        }
    }

    private void setStatusAndKey(OrdersStatusDTO ordersStatusDTO, Set<Orders> byUsers, Long mostBigKey) {
        for (Orders currentOrder : byUsers) {
            currentOrder.setStatus(ordersStatusDTO.getStatus());
            currentOrder.setKeyOrderProduct(mostBigKey + 1);
            if (ordersStatusDTO.getPayment().equals("payment_confirm")) {
                currentOrder.setPaid(true);
            }
            if (isCodeValid) {
                currentOrder.setPrice(currentOrder.getPrice() - currentOrder.getPrice() * 0.10);
            }
            this.ordersRepository.save(currentOrder);
        }
    }

    private PromoCodes validateCode(String promoCode, Users user) {
        return this.productRepository.findPromoCodeByUser(promoCode, user.getId());
    }

    private static Long getKey(Set<Orders> lastKey) {
        Long mostBigKey = 0L;
        for (Orders order : lastKey) {
            if (order.getKeyOrderProduct() == null) {
                continue;
            }
            if (order.getKeyOrderProduct() > mostBigKey) {
                mostBigKey = order.getKeyOrderProduct();
            }
        }
        return mostBigKey;
    }

    @Override
    public Set<OrdersDTO> findByStatus(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            Set<OrdersDTO> returned = this.ordersRepository.findByStatus(statusConf);
            for (OrdersDTO current : returned) {
                current.getUsers().setPassword(null);
                current.getUsers().setAuthorities(null);
                current.getUsers().setLikeProducts(null);
                current.getUsers().setVerificationCode(null);
            }
            return returned;
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    @Override
    public boolean findByUsersId(Long id) {
        OrdersProcessing ordersProcessing = new OrdersProcessing();
        synchronized (this) {
            Set<Orders> byOrderKey = new HashSet<>(ordersInProcessing);
            if (byOrderKey.stream().allMatch(order -> order.getDateOfDelivery() == null)) {
                return false;
            }
            double totalPrice = byOrderKey.stream().mapToDouble(Orders::getPrice).sum();
            ordersProcessing.setTotalPrice(totalPrice);
            Optional<Users> currentUser = this.usersRepository.findUserBayKey(id);
            ordersProcessing.setUser(currentUser.get());
            ordersProcessing.setStatusOrder(statusSend);
            ordersProcessing.setDateOfDispatch(LocalDate.now());
            ordersProcessing.setKeyOrder(id);
            if (byOrderKey.stream().findFirst().get().isPaid()) {
                ordersProcessing.setPaid(true);
            }
            this.ordersProcessingRepository.save(ordersProcessing);
            return true;
        }
    }

    @Override
    public Set<Orders> getOrdersByKey(Long id) {
        ordersInProcessing = new HashSet<>();
        Set<Orders> orderForProcessing = this.ordersRepository.findByKeyOrderProduct(id);
        ordersInProcessing.addAll(orderForProcessing);
        return orderForProcessing;
    }


    @Override
    public boolean updateStatusSend(OrderStatusSendAdmin orderStatusSendAdmin, Long id) throws ParseException {
        synchronized (this) {
            Set<Orders> orders = getOrdersByKey(id);
            LocalDate localDate = LocalDate.parse(orderStatusSendAdmin.getDateDelivery());
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            java.sql.Time timeDelivery = new java.sql.Time(formatter
                    .parse(String.valueOf(orderStatusSendAdmin.getTimeDelivery())).getTime());
            java.sql.Time timeNow = Time.valueOf(LocalTime.now());
            if (localDate.isBefore(LocalDate.now()) || timeDelivery.before(timeNow)) {
                return false;
            }
            setStatusTimeAndDate(orderStatusSendAdmin, orders, localDate, timeDelivery);
        }
        return true;
    }

    private void setStatusTimeAndDate(OrderStatusSendAdmin orderStatusSendAdmin, Set<Orders> orders, LocalDate localDate, Time timeDelivery) {
        for (Orders currentOrder : orders) {
            currentOrder.setStatus(orderStatusSendAdmin.getStatus());
            currentOrder.setDateOfDelivery(localDate);
            currentOrder.setTimeOfDelivery(timeDelivery);
            currentOrder.setStatus(statusSend);
            this.ordersRepository.save(currentOrder);
        }
    }

    @Override
    public void updateStatusDelivery(OrderStatusDeliveryAdmin orderStatusDeliveryAdmin, Long id) {
        synchronized (this) {
            Set<OrdersProcessing> orders = this.ordersProcessingRepository.findOrderById(id);
            getStatus(orderStatusDeliveryAdmin, orders);
            Long keyOrder = getKeyOrder(orders);
            Set<Orders> ordersForChangeStatus = getOrdersByKey(keyOrder);
            setStatusAndDate(orders, ordersForChangeStatus);
        }
    }

    private void setStatusAndDate(Set<OrdersProcessing> orders, Set<Orders> ordersForChangeStatus) {
        LocalDate dateRecipe = getLocalDate(ordersForChangeStatus);
        setDateRecipe(orders, dateRecipe);
    }

    private void setDateRecipe(Set<OrdersProcessing> orders, LocalDate dateRecipe) {
        for (OrdersProcessing order : orders) {
            order.setDateOfReceipt(dateRecipe);
            order.setPaid(true);
            this.ordersProcessingRepository.save(order);
        }
    }

    @Nullable
    private LocalDate getLocalDate(Set<Orders> ordersForChangeStatus) {
        LocalDate dateRecipe = null;
        for (Orders currentOrder : ordersForChangeStatus) {
            currentOrder.setStatus(statusDelivery);
            currentOrder.setPaid(true);
            this.ordersRepository.save(currentOrder);
            dateRecipe = currentOrder.getDateOfDelivery();
        }
        return dateRecipe;
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

    @Override
    public Set<OrdersDTO> trackingByStatus(Users user) {
        Set<OrdersDTO> orders = this.ordersRepository.findConfirmedOrder(user.getId(), statusConf,
                statusSend);
        Map<Long, Double> totalPrice = new HashMap<>();
        for (OrdersDTO currentOrder : orders) {
            if (!totalPrice.containsKey(currentOrder.getKeyOrderProduct())) {
                totalPrice.put(currentOrder.getKeyOrderProduct(), currentOrder.getPrice());
            } else {
                totalPrice.put(currentOrder.getKeyOrderProduct(),
                        totalPrice.get(currentOrder.getKeyOrderProduct()) + currentOrder.getPrice());
            }
        }
        for (OrdersDTO current : orders) {
            for (Long key : totalPrice.keySet()) {
                if (Objects.equals(current.getKeyOrderProduct(), key)) {
                    current.setTotalPrice(totalPrice.get(current.getKeyOrderProduct()));
                }
            }
        }
        return orders;
    }

    @Override
    public Set<OrdersDTO> findOrdersWhichNotDelivered(Users user) {
        return this.ordersRepository.findNotDeliveredOrders(user.getId(), statusNewOrder);

    }

    @Override
    public Set<OrdersDTO> findOrdersWhichConfirmed(Users user) {
        return this.ordersRepository.findConfirmedOrder(user.getId(), statusConf, statusSend);
    }

    @Override
    public Set<OrdersDTO> findAllNotSendOrders() {
        return this.ordersRepository.findAllNotSendOrders(statusConf, statusSend);
    }

    @Override
    public Set<PromoCodesDTO> findUserPromoCodes(Users user) {
        return this.promoCodesRepository.findUserPromoCodes(user.getId());
    }
}
