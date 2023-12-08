package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.HomeController;
import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
import com.example.pastry.shop.service.impl.HomeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Home")
public class HomeControllerImpl implements HomeController {

    private final HomeServiceImpl homeService;

    public HomeControllerImpl(HomeServiceImpl homeService) {
        this.homeService = homeService;
    }

    @Operation(summary = "Get most ordered products")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get most ordered products",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MostOrderedProductsDTO.class)))}
    )
    @Override
    public ResponseEntity<?> getMostOrderedProducts() {
        List<MostOrderedProductsDTO> mostOrderedProductsDTOS = this.homeService.findMostOrderedProducts();
        return ResponseEntity.ok(mostOrderedProductsDTOS);
    }

    @Operation(summary = "Get most recommended products")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get most recommended products",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MostOrderedProductsDTO.class)))}
    )
    @Override
    public ResponseEntity<?> getRecommendedProducts() {
        List<MostOrderedProductsDTO> recommendedProducts = this.homeService.findRecommendedProducts();
        return ResponseEntity.ok(recommendedProducts);
    }
}
