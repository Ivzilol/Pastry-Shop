package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.service.ProductsService;
import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.AuthorityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductsService productsService;

    private final UserService userService;

    public ProductController(ProductsService productsService, UserService userService) {
        this.productsService = productsService;
        this.userService = userService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Products product = productsService.createProduct(createProductDTO);
        return ResponseEntity.ok(product);
    }

    @GetMapping("")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal Users user) {
        Set<Products> shopById = productsService.findByUser(user);
        return ResponseEntity.ok(shopById);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        Optional<Products> productOpt = productsService.findById(productId);
        return ResponseEntity.ok(productOpt);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody Products product,
                                           @AuthenticationPrincipal Users user) {
        // add admin in this product if it must be changed
        if (product.getAdmin() != null) {
            Users admin = product.getAdmin();
            admin = userService.findUserByUsername(admin.getUsername()).orElse(new Users());
            if (AuthorityUtil.hasRole(AuthorityEnum.admin.name(), admin)) {
                product.setAdmin(admin);
            }
        }
        Products updateProduct = productsService.saveProduct(product);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productsService.delete(productId);
            return ResponseEntity.ok("Product Delete");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
