package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.AllShopsDTO;
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

    public ShopsController(ShopsService productService) {
        this.shopsService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> createShop(@AuthenticationPrincipal Users user) {
        Shops newShop = shopsService.createShop(user);
        return ResponseEntity.ok(newShop);
    }

    @GetMapping("")
    public ResponseEntity<?> getShop() {
        List<AllShopsDTO> allShopsDTO = shopsService.findAll();
        return ResponseEntity.ok(allShopsDTO);
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
                                        @RequestBody  Shops shop) {
        Shops updateShop = shopsService.saveShop(shop, user);
        AllShopsDTO allShopsDTO = new AllShopsDTO(
                updateShop.getId(), updateShop.getNumber(), updateShop.getName(),
                updateShop.getStatus(), updateShop.getTown(), updateShop.getAddress()
        );
        return ResponseEntity.ok(allShopsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.shopsService.deleteShop(id, user);
        return ResponseEntity.ok().build();
    }
}
