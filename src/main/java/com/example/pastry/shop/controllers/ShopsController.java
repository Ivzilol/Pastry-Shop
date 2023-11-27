package com.example.pastry.shop.controllers;


import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/shops")
public interface ShopsController {

    @PostMapping("")
    ResponseEntity<?> createShop(@AuthenticationPrincipal Users user);

    @GetMapping("")
    ResponseEntity<?> getShop();

    @GetMapping("/{shopId}")
    ResponseEntity<?> getShop(@PathVariable Long shopId);

    @PatchMapping("/{shopId}")
    ResponseEntity<?> updateShop(@AuthenticationPrincipal Users user,
                                 @PathVariable Long shopId,
                                 @RequestBody Shops shop);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteShop(@PathVariable Long id,
                                 @AuthenticationPrincipal Users user);
}
