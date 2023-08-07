package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.ShopResponseDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.ShopsService;
import com.example.pastry.shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class ShopsController {


    private final ShopsService shopsService;

    private final UserService userService;

    public ShopsController(ShopsService productService, UserService userService) {
        this.shopsService = productService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> createShop(@AuthenticationPrincipal Users user) {
        Shops newShop = shopsService.createShop(user);
        return ResponseEntity.ok(newShop);
    }

    @GetMapping("")
    public ResponseEntity<?> getShop() {
        List<Shops> shopsByUsers = shopsService.findAll();
        return ResponseEntity.ok(shopsByUsers);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShop(@PathVariable Long shopId) {
        Optional<Shops> shopOpt = shopsService.findById(shopId);
        ShopResponseDTO response = new ShopResponseDTO(shopOpt.orElse(new Shops()));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@AuthenticationPrincipal Users user,
                                        @PathVariable Long shopId,
                                        @RequestBody Shops shop) {
        Shops updateShop = shopsService.saveShop(shop, user);
        return ResponseEntity.ok(updateShop);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.shopsService.deleteShop(id, user);
        return ResponseEntity.ok().build();
    }
}
