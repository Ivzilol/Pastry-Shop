package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.pastry.shop.common.ConstantMessages.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
@Tag(name = "Products")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Operation(summary = "Create product")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Product was created",
                    content = {@Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(description = "Incorrect field",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorProductDTO.class))})
            }
    )
    @PostMapping(value = "/create/admin", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(
            @RequestPart(value = "imageUrl", required = false) MultipartFile file,
            @RequestPart(value = "dto") @Valid CreateProductDTO createProductDTO, BindingResult result
    ) throws IOException {
        ResponseEntity<ErrorProductDTO> errorProductDTO = errorsCreateProduct(result);
        if (errorProductDTO != null) return errorProductDTO;
        this.productsService.createProduct(createProductDTO, file);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(SUCCESSFUL_CREATE_PRODUCT);
        return ResponseEntity.ok(customResponse);
    }

    @Nullable
    private ResponseEntity<ErrorProductDTO> errorsCreateProduct(BindingResult result) {
        ErrorProductDTO errorProductDTO = new ErrorProductDTO();
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            this.productsService.setErrors(errors, errorProductDTO);
            return ResponseEntity.ok().body(errorProductDTO);
        }
        return null;
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get product by ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        Optional<GetProductsDTO> productOpt = productsService.findById(productId);
        return ResponseEntity.ok(productOpt);
    }

    @Operation(summary = "Update product")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful update product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(responseCode = "200", description = "Unsuccessful update product!",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))})
            }
    )
    @PatchMapping("/edit/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestPart(value = "imageUrl", required = false) MultipartFile file,
                                           @RequestPart(value = "dto") UpdateProductDTO updateProductDTO) throws IOException {
        boolean updateProduct = this.productsService.saveProduct(updateProductDTO, productId, file);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(updateProduct ? SUCCESSFUL_UPDATE_PRODUCT : UNSUCCESSFUL_UPDATE_PRODUCT);
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Delete product")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful delete product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(responseCode = "500", description = "Unsuccessful delete product!")
            }
    )
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productsService.delete(productId);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(SUCCESSFUL_DELETE_PRODUCT_ADMIN);
            return ResponseEntity.ok(customResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get products", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful get products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductsDTO.class))})
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal Users user) {
        List<ProductsDTO> productsDTOS = this.productsService.getProductsForDTO(user);
        return ResponseEntity.ok(productsDTOS);
    }

    @Operation(summary = "Like product", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful like product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))})
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> likeProduct(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        this.productsService.likeProduct(id, user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(SUCCESSFUL_LIKE_PRODUCT);
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Dislike product", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful dislike product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))})
            }
    )
    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<?> dislikeProduct(@PathVariable Long id,
                                            @AuthenticationPrincipal Users user) {
        this.productsService.deleteFromProductsLikes(id, user);
        CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(SUCCESSFUL_DISLIKE_PRODUCT);
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Get pie category")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful response pie category products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @GetMapping("/pies")
    public ResponseEntity<?> getPies() {
        Set<GetProductsDTO> pies = this.productsService.findAllPies();
        return ResponseEntity.ok(pies);
    }

    @Operation(summary = "Get buns category")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful response buns category products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @GetMapping("/buns")
    public ResponseEntity<?> getBuns() {
        Set<GetProductsDTO> buns = this.productsService.findAllBuns();
        return ResponseEntity.ok(buns);
    }

    @Operation(summary = "Get sweet category")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful response sweet category products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @GetMapping("/sweets")
    public ResponseEntity<?> getSweets() {
        Set<GetProductsDTO> sweets = this.productsService.findAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @Operation(summary = "Get cake category")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful response cake category products",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @GetMapping("/cakes")
    public ResponseEntity<?> getCakes() {
        Set<GetProductsDTO> cakes = this.productsService.findAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @Operation(summary = "Response products by category")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Response products by category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProductsDTO.class))})
            }
    )
    @PostMapping("/search")
    public ResponseEntity<?> getSearchProducts(@RequestBody CategoryProductDto categoryProductDto) {
        Set<GetProductsDTO> searchedProducts = this.productsService.findSearchedProducts(categoryProductDto);
        return ResponseEntity.ok(searchedProducts);
    }
}
