package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CategoryProductDto;
import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.dto.UpdateProductDTO;
import com.example.pastry.shop.model.entity.Users;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/products")
public interface ProductController {

    @PostMapping(value = "/create/admin", consumes = {"multipart/form-data"})
    ResponseEntity<?> createProduct(
            @RequestPart(value = "imageUrl", required = false) MultipartFile file,
            @RequestPart(value = "dto") @Valid CreateProductDTO createProductDTO, BindingResult result
    ) throws IOException;

    @GetMapping("/{productId}")
    ResponseEntity<?> getProduct(@PathVariable Long productId);

    @PatchMapping("/edit/{productId}")
    ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                    @RequestPart(value = "imageUrl", required = false) MultipartFile file,
                                    @RequestPart(value = "dto") UpdateProductDTO updateProductDTO) throws IOException;

    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable Long productId);

    @GetMapping("")
    ResponseEntity<?> getProduct(@AuthenticationPrincipal Users user);

    @PatchMapping("/{id}")
    ResponseEntity<?> likeProduct(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user);
    @DeleteMapping("/dislike/{id}")
    ResponseEntity<?> dislikeProduct(@PathVariable Long id,
                                     @AuthenticationPrincipal Users user);

    @GetMapping("/pies")
    ResponseEntity<?> getPies();

    @GetMapping("/buns")
    ResponseEntity<?> getBuns();

    @GetMapping("/sweets")
    ResponseEntity<?> getSweets();

    @GetMapping("/cakes")
    ResponseEntity<?> getCakes();

    @PostMapping("/search")
    ResponseEntity<?> getSearchProducts(@RequestBody CategoryProductDto categoryProductDto);
}
