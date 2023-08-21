package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.OrderProcessingService;
import com.example.pastry.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class OrdersController {


    private final OrderService orderService;

    private final OrderProcessingService orderProcessingService;

    public OrdersController(OrderService orderService, OrderProcessingService orderProcessingService) {
        this.orderService = orderService;
        this.orderProcessingService = orderProcessingService;
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> createOrder(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        Orders createOrder = this.orderService.createOrder(id, user);
        return ResponseEntity.ok(createOrder);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal Users user) {
        Set<Orders> ordersById = orderService.findByUser(user);
        return ResponseEntity.ok(ordersById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductFromOrders(@PathVariable Long id) {
        this.orderService.removeProduct(id);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PatchMapping("")
    public ResponseEntity<Orders> updateStatusOrder(@RequestBody OrdersStatusDTO ordersStatusDTO,
                                                    @AuthenticationPrincipal Users user) {
        Orders orders = this.orderService.updateStatus(ordersStatusDTO, user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllConfirmedOrders(@AuthenticationPrincipal Users user) {
        Set<Orders> confirmedOrders = this.orderService.findByStatus(user);
        return ResponseEntity.ok(confirmedOrders);
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<?> startProcessingOrder(@PathVariable Long id) {
        Set<Orders> currentOrders = this.orderService.findByUsersId(id);
        return ResponseEntity.ok(currentOrders);
    }

    @GetMapping("/admin/send")
    public ResponseEntity<?> getAllSendOrders(@AuthenticationPrincipal Users user) {
        Set<OrdersProcessing> sendOrders = this.orderProcessingService.findByStatus(user);
        return ResponseEntity.ok(sendOrders);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Orders> updateStatusOrderSend(@RequestBody OrderStatusSendAdmin orderStatusSendAdmin,
                                                        @PathVariable Long id) throws ParseException {
        Orders order = this.orderService.updateStatusSend(orderStatusSendAdmin, id);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/admin/delivery/{id}")
    public ResponseEntity<OrdersProcessing> updateStatusOrderSend(@RequestBody OrderStatusDeliveryAdmin orderStatusDeliveryAdmin,
                                                                  @PathVariable Long id) {
        OrdersProcessing order = this.orderService.updateStatusDelivery(orderStatusDeliveryAdmin, id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/tracking")
    public ResponseEntity<?> getConfirmedOrder(@AuthenticationPrincipal Users user) {
        Set<Orders> confirmedOrder = this.orderService.trackingByStatus(user);
        return ResponseEntity.ok(confirmedOrder);
    }

    @GetMapping("/history/user")
    public ResponseEntity<?> getOrdersByCurrentUser(@AuthenticationPrincipal Users user) {
        Set<OrdersProcessing> userOrders = this.orderProcessingService.findOrdersCurrentUser(user);
        return ResponseEntity.ok(userOrders);
    }

    @GetMapping("/history/admin")
    public ResponseEntity<?> getAllOrders() {
        Set<OrdersProcessing> allOrders = this.orderProcessingService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrdersStatus(@AuthenticationPrincipal Users user) {
        Set<Orders> userOrder = this.orderService.findOrdersWhichNotDelivered(user);
        return ResponseEntity.ok(userOrder);
    }

    @GetMapping("status/confirmed")
    public ResponseEntity<?> getOrderStatusConfirmed(@AuthenticationPrincipal Users user) {
        Set<Orders> userOrders = this.orderService.findOrdersWhichConfirmed(user);
        return ResponseEntity.ok(userOrders);
    }

}
