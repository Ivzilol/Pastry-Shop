package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.MostOrderedProductsDTO;
import com.example.pastry.shop.service.impl.HomeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Home")
public class HomeController {

    private final HomeServiceImpl homeService;

    public HomeController(HomeServiceImpl homeService) {
        this.homeService = homeService;
    }

    @Operation(summary = "Get most ordered products")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get most ordered products",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MostOrderedProductsDTO.class)))}
    )
    @GetMapping("")
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
    @GetMapping("/home")
    public ResponseEntity<?> getRecommendedProducts() {
        List<MostOrderedProductsDTO> recommendedProducts = this.homeService.findRecommendedProducts();
        return ResponseEntity.ok(recommendedProducts);
    }
}
