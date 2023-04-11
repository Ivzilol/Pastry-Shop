package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.ShopResponseDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.service.ShopsService;
import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.AuthorityUtil;
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
    public ResponseEntity<?> getShop(@AuthenticationPrincipal Users user) {
        Set<Shops> shopsByUsers = shopsService.findByUser(user);
        return ResponseEntity.ok(shopsByUsers);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShop(@PathVariable Long shopId) {
        Optional<Shops> shopOpt = shopsService.findById(shopId);
        ShopResponseDTO response = new ShopResponseDTO(shopOpt.orElse(new Shops()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@PathVariable Long shopId,
                                        @RequestBody Shops shop,
                                        @AuthenticationPrincipal Users user) {
        // add moderator in this shop if it must be changed
        if (shop.getAdmin() != null) {
            Users moderator = shop.getAdmin();
            moderator = userService.findUserByUsername(moderator.getUsername()).orElse(new Users());
            if (AuthorityUtil.hasRole(AuthorityEnum.moderator.name(), moderator)) {
                shop.setAdmin(moderator);
            }
        }
        Shops updateShop = shopsService.saveShop(shop);
        return ResponseEntity.ok(updateShop);
    }
}
