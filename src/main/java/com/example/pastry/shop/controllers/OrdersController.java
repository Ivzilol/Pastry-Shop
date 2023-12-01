package com.example.pastry.shop.controllers;


import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RequestMapping("/api/orders")
public interface OrdersController {

    @PostMapping("/{id}")
    ResponseEntity<?> createOrder(@PathVariable Long id,
                                  @AuthenticationPrincipal Users user);
    @GetMapping("")
    ResponseEntity<?> getOrdersByUser(@AuthenticationPrincipal Users user);
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProductFromOrders(@PathVariable Long id);

    @PatchMapping("")
    ResponseEntity<?> updateStatusOrder(@RequestBody OrdersStatusDTO ordersStatusDTO,
                                        @AuthenticationPrincipal Users user);
    @GetMapping("/admin")
    ResponseEntity<?> getAllConfirmedOrders(@AuthenticationPrincipal Users user);

    @GetMapping("/admin/send")
    ResponseEntity<?> getAllSendOrders(@AuthenticationPrincipal Users user);

    @PatchMapping("/{id}")
    ResponseEntity<?> updateStatusOrderSend(@RequestBody OrderStatusSendAdmin orderStatusSendAdmin,
                                            @PathVariable Long id) throws ParseException;
    @PatchMapping("/admin/delivery/{id}")
    ResponseEntity<?> updateStatusOrderSend(@RequestBody OrderStatusDeliveryAdmin orderStatusDeliveryAdmin,
                                            @PathVariable Long id);
    @GetMapping("/tracking")
    ResponseEntity<?> getConfirmedOrder(@AuthenticationPrincipal Users user);

    @GetMapping("/history/user")
    ResponseEntity<?> getOrdersByCurrentUser(@AuthenticationPrincipal Users user);

    @GetMapping("/history/admin")
    ResponseEntity<?> getAllOrders();

    @GetMapping("/status")
    ResponseEntity<?> getOrdersStatus(@AuthenticationPrincipal Users user);

    @GetMapping("status/confirmed")
    ResponseEntity<?> getOrderStatusConfirmed(@AuthenticationPrincipal Users user);

    @GetMapping("status/confirmed/admin")
    ResponseEntity<?> getNotSendOrders();

    @PatchMapping("pay-with-card")
    ResponseEntity<?> payOrderWithCard(@AuthenticationPrincipal Users user);

    @GetMapping("user-promo-codes")
    ResponseEntity<?> userPromoCodes(@AuthenticationPrincipal Users user);
}
