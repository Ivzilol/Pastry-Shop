package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.OrdersProcessingController;
import com.example.pastry.shop.model.dto.OrdersProcessingDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.OrderProcessingServiceImpl;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import static com.example.pastry.shop.common.ConstantMessages.SUCCESSFUL_START_PROCESSING_ORDER;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Orders Processing")
public class OrdersProcessingControllerImpl implements OrdersProcessingController {

    private final OrderServiceImpl orderService;

    private final OrderProcessingServiceImpl orderProcessingService;

    public OrdersProcessingControllerImpl(OrderServiceImpl orderService, OrderProcessingServiceImpl orderProcessingService) {
        this.orderService = orderService;
        this.orderProcessingService = orderProcessingService;
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
    @Override
    public ResponseEntity<?> startProcessingOrder(Long id) {
        boolean isStart = this.orderService.findByUsersId(id);
        CustomResponse customResponse = new CustomResponse();
        if (isStart) {
            customResponse.setCustom(SUCCESSFUL_START_PROCESSING_ORDER);
        }
        return ResponseEntity.ok(customResponse);
    }

    @Override
    public ResponseEntity<?> getOrdersByDate(Users user , LocalDate startDate, LocalDate endDate) {
        Set<OrdersProcessingDTO> ordersProcessingDTO =
                this.orderProcessingService.findOrdersByDate(user ,startDate, endDate);
        return ResponseEntity.ok(ordersProcessingDTO);
    }
}
