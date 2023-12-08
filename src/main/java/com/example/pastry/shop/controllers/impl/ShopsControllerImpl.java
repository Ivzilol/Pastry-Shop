package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.ShopsController;
import com.example.pastry.shop.model.dto.ShopDTO;
import com.example.pastry.shop.model.dto.ShopsDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.ShopsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.example.pastry.shop.common.ConstantMessages.SUCCESSFUL_UPDATE_SHOP;
import static com.example.pastry.shop.common.ConstantMessages.UNSUCCESSFUL_UPDATE_SHOP;


@RestController
@Tag(name = "Shops")
public class ShopsControllerImpl implements ShopsController {


    private final ShopsServiceImpl shopsService;

    public ShopsControllerImpl(ShopsServiceImpl productService) {
        this.shopsService = productService;
    }

    @Operation(summary = "Create Shop", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful create shop")
            }
    )
    @Override
    public ResponseEntity<?> createShop(Users user) {
        shopsService.createShop(user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Successful create shop");
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Get Shop")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get Shop",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopsDTO.class))),
                    }
    )
    @Override
    public ResponseEntity<?> getShop() {
        List<ShopsDTO> shopsDTO = shopsService.findAll();
        return ResponseEntity.ok(shopsDTO);
    }

    @Operation(summary = "Get Shop by ID")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get Shop by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopsDTO.class))),
            }
    )
    @Override
    public ResponseEntity<?> getShop(Long shopId) {
        Optional<ShopDTO> shopOpt = shopsService.findById(shopId);
        return ResponseEntity.ok(shopOpt);
    }

    @Operation(summary = "Update Shop", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful update shop",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(description = "Incorrect field",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))}),
            }
    )
    @Override
    public ResponseEntity<?> updateShop(Users user, Long shopId, Shops shop) {
        boolean isUpdate = this.shopsService.updateShop(shop, shopId, user);
        CustomResponse customResponse = new CustomResponse();
        if (isUpdate) {
            customResponse.setCustom(SUCCESSFUL_UPDATE_SHOP);
        } else {
            customResponse.setCustom(UNSUCCESSFUL_UPDATE_SHOP);
        }
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Delete Shop", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful delete shop")
            }
    )
    @Override
    public ResponseEntity<?> deleteShop(Long id, Users user) {
        this.shopsService.deleteShop(id, user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Successful delete shop");
        return ResponseEntity.ok(customResponse);
    }
}
