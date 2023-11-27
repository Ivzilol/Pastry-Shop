package com.example.pastry.shop.controllers;

import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.pastry.shop.common.ConstantMessages.SUCCESSFUL_START_PROCESSING_ORDER;

@RestController
@RequestMapping("/api/orders-processing")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Orders Processing")
public class OrdersProcessingController {

    private final OrderServiceImpl orderService;

    public OrdersProcessingController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Start processing order", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Start processing order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @PostMapping("/admin/{id}")
    public ResponseEntity<?> startProcessingOrder(@PathVariable Long id) {
        boolean isStart = this.orderService.findByUsersId(id);
        CustomResponse customResponse = new CustomResponse();
        if (isStart) {
            customResponse.setCustom(SUCCESSFUL_START_PROCESSING_ORDER);
        }
        return ResponseEntity.ok(customResponse);
    }
}
