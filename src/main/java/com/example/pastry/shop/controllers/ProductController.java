package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.ProductsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:3000", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "/create/admin", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(
            @RequestPart(value = "imageUrl") MultipartFile file,
            @RequestPart(value = "dto") CreateProductDTO createProductDTO
    ) throws IOException {
        this.productsService.createProduct(createProductDTO, file);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        Optional<GetProductsDTO> productOpt = productsService.findById(productId);
        return ResponseEntity.ok(productOpt);
    }

    @PatchMapping( "/edit/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestPart(value = "imageUrl") MultipartFile file,
                                           @RequestPart(value = "dto") UpdateProductDTO updateProductDTO) throws IOException {
        boolean updateProduct = this.productsService.saveProduct(updateProductDTO, productId, file);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(updateProduct ? "Successful update product!" : "Unsuccessful update product!");
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productsService.delete(productId);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Product Delete");
            return ResponseEntity.ok(customResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> likeProduct(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        this.productsService.likeProduct(id, user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Like");
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<?> dislikeProduct(@PathVariable Long id,
                                            @AuthenticationPrincipal Users user) {
        this.productsService.deleteFromProductsLikes(id, user);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom("Not Like");
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/pies")
    public ResponseEntity<?> getPies() {
        Set<GetProductsDTO> pies = this.productsService.findAllPies();
        return ResponseEntity.ok(pies);
    }

    @GetMapping("/buns")
    public ResponseEntity<?> getBuns() {
        Set<GetProductsDTO> buns = this.productsService.findAllBuns();
        return ResponseEntity.ok(buns);
    }

    @GetMapping("/sweets")
    public ResponseEntity<?> getSweets() {
        Set<GetProductsDTO> sweets = this.productsService.findAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/cakes")
    public ResponseEntity<?> getCakes() {
        Set<GetProductsDTO> cakes = this.productsService.findAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getSearchProducts(@RequestBody CategoryProductDto categoryProductDto) {
        Set<GetProductsDTO> searchedProducts = this.productsService.findSearchedProducts(categoryProductDto);
        return ResponseEntity.ok(searchedProducts);
    }

    @GetMapping("")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal Users user) {
        List<ProductsDTO> productsDTOS = this.productsService.getProductsForDTO(user);
        return ResponseEntity.ok(productsDTOS);
    }
}
