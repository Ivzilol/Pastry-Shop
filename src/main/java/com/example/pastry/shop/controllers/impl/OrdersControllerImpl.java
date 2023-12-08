package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.OrdersController;
import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Orders;
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
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Set;

import static com.example.pastry.shop.common.ConstantMessages.*;
import static com.example.pastry.shop.common.ExceptionMessages.DATA_TIME_FEATURE;
import static com.example.pastry.shop.common.ExceptionMessages.INVALID_PROMO_CODE;

@RestController
@Tag(name = "Orders")
public class OrdersControllerImpl implements OrdersController {


    private final OrderServiceImpl orderService;
    private final OrderProcessingServiceImpl orderProcessingService;


    public OrdersControllerImpl(OrderServiceImpl orderService, OrderProcessingServiceImpl orderProcessingService) {
        this.orderService = orderService;
        this.orderProcessingService = orderProcessingService;
    }


    @Operation(summary = "Create order", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Create order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> createOrder(Long id, Users user) {
        Orders createOrder = this.orderService.createOrder(id, user);
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setProductName(createOrder.getProductName());
        ordersDTO.setPrice(createOrder.getPrice());
        return ResponseEntity.ok(ordersDTO);
    }

    @Operation(summary = "Get Order By User", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get Order By User",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getOrdersByUser(Users user) {
        Set<OrdersDTO> ordersById = orderService.findByUser(user);
        return ResponseEntity.ok(ordersById);
    }

    @Operation(summary = "Delete product from Order")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Delete product from Order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @Override
    public ResponseEntity<?> deleteProductFromOrders(Long id) {
        this.orderService.removeProduct(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(SUCCESSFUL_DELETE_PRODUCT);
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Update status on Order", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Update status on Order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @Override
    public ResponseEntity<?> updateStatusOrder(OrdersStatusDTO ordersStatusDTO, Users user) {
        boolean isSuccessful = this.orderService.updateStatus(ordersStatusDTO, user);
        CustomResponse customResponse = new CustomResponse();
        if (isSuccessful) {
            customResponse.setCustom(SUCCESSFUL_CONFIRM_PRODUCT);
        } else {
            customResponse.setCustom(INVALID_PROMO_CODE);
        }
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Get all confirmed orders", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get all confirmed orders",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getAllConfirmedOrders(Users user) {
        Set<OrdersDTO> confirmedOrders = this.orderService.findByStatus(user);
        return ResponseEntity.ok(confirmedOrders);
    }


    @Operation(summary = "Get all send orders", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get all send orders",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersProcessingDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getAllSendOrders(Users user) {
        Set<OrdersProcessingDTO> sendOrders = this.orderProcessingService.findByStatus(user);
        return ResponseEntity.ok(sendOrders);
    }

    @Operation(summary = "Update status on order to send", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Update status on order to send",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @Override
    public ResponseEntity<?> updateStatusOrderSend(OrderStatusSendAdmin orderStatusSendAdmin,
                                                   Long id) throws ParseException {
        boolean isUpdated = this.orderService.updateStatusSend(orderStatusSendAdmin, id);
        CustomResponse customResponse = new CustomResponse();
        if (isUpdated) {
            customResponse.setCustom(SUCCESSFUL_UPDATE_ORDER_SEND);
        } else {
            customResponse.setCustom(DATA_TIME_FEATURE);
        }
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Update status on order to delivery", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Update status on order to delivery",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class)))
            }
    )
    @Override
    public ResponseEntity<?> updateStatusOrderSend(OrderStatusDeliveryAdmin orderStatusDeliveryAdmin,
                                                   Long id) {
        this.orderService.updateStatusDelivery(orderStatusDeliveryAdmin, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(SUCCESSFUL_UPDATE_ORDER_DELIVERY);
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "User tracking order", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User tracking order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getConfirmedOrder(Users user) {
        Set<OrdersDTO> confirmedOrder = this.orderService.trackingByStatus(user);
        return ResponseEntity.ok(confirmedOrder);
    }

    @Operation(summary = "User orders history", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User orders history",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersProcessingDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getOrdersByCurrentUser(Users user) {
        Set<OrdersProcessingDTO> userOrders = this.orderProcessingService.findOrdersCurrentUser(user);
        return ResponseEntity.ok(userOrders);
    }


    @Operation(summary = "Admin get all users orders")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Admin get all users orders",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersProcessingDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getAllOrders() {
        Set<OrdersProcessingDTO> allOrders = this.orderProcessingService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }


    @Operation(summary = "Get orders by status", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get orders by status",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getOrdersStatus(Users user) {
        Set<OrdersDTO> userOrder = this.orderService.findOrdersWhichNotDelivered(user);
        return ResponseEntity.ok(userOrder);
    }

    @Operation(summary = "Get orders with status confirmed", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get orders with status confirmed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getOrderStatusConfirmed(Users user) {
        Set<OrdersDTO> userOrders = this.orderService.findOrdersWhichConfirmed(user);
        return ResponseEntity.ok(userOrders);
    }

    @Operation(summary = "Get orders which not send", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get orders which not send",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrdersDTO.class)))
            }
    )
    @Override
    public ResponseEntity<?> getNotSendOrders() {
        Set<OrdersDTO> allNotSendOrders = this.orderService.findAllNotSendOrders();
        return ResponseEntity.ok(allNotSendOrders);
    }

    @Override
    public ResponseEntity<?> payOrderWithCard(Users user) {
        return null;
    }

    @Override
    public ResponseEntity<?> userPromoCodes(Users user) {
        Set<PromoCodesDTO> promoCodesDTO = this.orderService.findUserPromoCodes(user);
        return ResponseEntity.ok(promoCodesDTO);
    }
}
