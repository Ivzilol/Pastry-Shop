package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.util.service.ShopsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Component
@RestController
@RequestMapping("/api/shops")
public class ShopsController {


    private final ShopsService shopsService;

    public ShopsController(ShopsService productService) {
        this.shopsService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal Users user) {
        Shops newShop = shopsService.createShop(user);

        return ResponseEntity.ok(newShop);
    }

    @GetMapping("")
    public ResponseEntity<?> getShop(@AuthenticationPrincipal Users user) {
        Set<Shops> shopsByUsers = shopsService.findByUser(user);
        return ResponseEntity.ok(shopsByUsers);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShop(@PathVariable Long shopId, @AuthenticationPrincipal Users user) {
        Optional<Shops> shopOpt = shopsService.findById(shopId);
        return ResponseEntity.ok(shopOpt.orElse(new Shops()));
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopId,
                                        @RequestBody Shops shop,
                                        @AuthenticationPrincipal Users user) {
        Shops updateShop = shopsService.saveShop(shop);
        return ResponseEntity.ok(updateShop);
    }
}
