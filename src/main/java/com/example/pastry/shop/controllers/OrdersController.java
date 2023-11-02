package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Orders;
import com.example.pastry.shop.model.entity.OrdersProcessing;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.OrderProcessingService;
import com.example.pastry.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
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
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setProductName(createOrder.getProductName());
        ordersDTO.setPrice(createOrder.getPrice());
        return ResponseEntity.ok(ordersDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal Users user) {
        Set<OrdersDTO> ordersById = orderService.findByUser(user);
        return ResponseEntity.ok(ordersById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductFromOrders(@PathVariable Long id) {
        this.orderService.removeProduct(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Delete product");
        return ResponseEntity.ok(customResponse);
    }

    @PatchMapping("")
    public ResponseEntity<?> updateStatusOrder(@RequestBody OrdersStatusDTO ordersStatusDTO,
                                                    @AuthenticationPrincipal Users user) {
        Orders orders = this.orderService.updateStatus(ordersStatusDTO, user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Confirm order");
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllConfirmedOrders(@AuthenticationPrincipal Users user) {
        Set<OrdersDTO> confirmedOrders = this.orderService.findByStatus(user);
        extracted(confirmedOrders);
        return ResponseEntity.ok(confirmedOrders);
    }


    @GetMapping("/admin/send")
    public ResponseEntity<?> getAllSendOrders(@AuthenticationPrincipal Users user) {
        Set<OrdersProcessingDTO> sendOrders = this.orderProcessingService.findByStatus(user);
        extractedOrderProcessing(sendOrders);
        return ResponseEntity.ok(sendOrders);
    }

    private static void extractedOrderProcessing(Set<OrdersProcessingDTO> sendOrders) {
        extractedOrderProcessingDto(sendOrders);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatusOrderSend(@RequestBody OrderStatusSendAdmin orderStatusSendAdmin,
                                                        @PathVariable Long id) throws ParseException {
        this.orderService.updateStatusSend(orderStatusSendAdmin, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Order send");
        return ResponseEntity.ok(customResponse);
    }

    @PatchMapping("/admin/delivery/{id}")
    public ResponseEntity<?> updateStatusOrderSend(@RequestBody OrderStatusDeliveryAdmin orderStatusDeliveryAdmin,
                                                                  @PathVariable Long id) {
        this.orderService.updateStatusDelivery(orderStatusDeliveryAdmin, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Order delivery");
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/tracking")
    public ResponseEntity<?> getConfirmedOrder(@AuthenticationPrincipal Users user) {
        Set<OrdersDTO> confirmedOrder = this.orderService.trackingByStatus(user);
        return ResponseEntity.ok(confirmedOrder);
    }

    @GetMapping("/history/user")
    public ResponseEntity<?> getOrdersByCurrentUser(@AuthenticationPrincipal Users user) {
        Set<OrdersProcessingDTO> userOrders = this.orderProcessingService.findOrdersCurrentUser(user);
        extractedOrderProcessingDto(userOrders);
        return ResponseEntity.ok(userOrders);
    }


    @GetMapping("/history/admin")
    public ResponseEntity<?> getAllOrders() {
        Set<OrdersProcessingDTO> allOrders = this.orderProcessingService.getAllOrders();
        extractedOrderProcessingDto(allOrders);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getOrdersStatus(@AuthenticationPrincipal Users user) {
        Set<OrdersDTO> userOrder = this.orderService.findOrdersWhichNotDelivered(user);
        return ResponseEntity.ok(userOrder);
    }

    @GetMapping("status/confirmed")
    public ResponseEntity<?> getOrderStatusConfirmed(@AuthenticationPrincipal Users user) {
        Set<OrdersDTO> userOrders = this.orderService.findOrdersWhichConfirmed(user);
        return ResponseEntity.ok(userOrders);
    }

    @GetMapping("status/confirmed/admin")
    public ResponseEntity<?> getNotSendOrders() {
        Set<OrdersDTO> allNotSendOrders = this.orderService.findAllNotSendOrders();
        return ResponseEntity.ok(allNotSendOrders);
    }

    private static void extracted(Set<OrdersDTO> confirmedOrders) {
        for (OrdersDTO current : confirmedOrders) {
            current.getUsers().setAuthorities(null);
            current.getUsers().setLikeProducts(null);
            current.getUsers().setVerificationCode(null);
        }
    }

    private static void extractedOrderProcessingDto(Set<OrdersProcessingDTO> userOrders) {
        for (OrdersProcessingDTO current: userOrders) {
            current.getUser().setAuthorities(null);
            current.getUser().setLikeProducts(null);
            current.getUser().setVerificationCode(null);
        }
    }

}
