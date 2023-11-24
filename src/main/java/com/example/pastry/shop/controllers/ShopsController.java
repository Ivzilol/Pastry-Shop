package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.ShopDTO;
import com.example.pastry.shop.model.dto.ShopsDTO;
import com.example.pastry.shop.model.dto.UsersDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.ShopsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Shops")
public class ShopsController {


    private final ShopsService shopsService;

    public ShopsController(ShopsService productService) {
        this.shopsService = productService;
    }

    @Operation(summary = "Create Shop", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful create shop")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> createShop(@AuthenticationPrincipal Users user) {
        shopsService.createShop(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getShop() {
        List<ShopsDTO> shopsDTO = shopsService.findAll();
        return ResponseEntity.ok(shopsDTO);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShop(@PathVariable Long shopId) {
        Optional<ShopDTO> shopOpt = shopsService.findById(shopId);
        return ResponseEntity.ok(shopOpt);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<?> updateShop(@AuthenticationPrincipal Users user,
                                        @PathVariable Long shopId,
                                        @RequestBody Shops shop) {
        boolean isUpdate = this.shopsService.updateShop(shop, shopId, user);
        CustomResponse customResponse = new CustomResponse();
        if (isUpdate) {
            customResponse.setCustom("Successful update shop");
        } else {
            customResponse.setCustom("Unsuccessful update shop");
        }
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.shopsService.deleteShop(id, user);
        return ResponseEntity.ok().build();
    }
}
