package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.CategoryProductDto;
import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.dto.GetProductsDTO;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "true", allowedHeaders = "true")
public class ProductController {

    private final ProductsService productsService;

    private final UserService userService;

    public ProductController(ProductsService productsService, UserService userService) {
        this.productsService = productsService;
        this.userService = userService;
    }

    @PostMapping("/create/admin")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        this.productsService.createProduct(createProductDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        Optional<GetProductsDTO> productOpt = productsService.findById(productId);
        return ResponseEntity.ok(productOpt);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody Products product,
                                           @AuthenticationPrincipal Users user) {

        this.productsService.saveProduct(product, user);
        return ResponseEntity.ok().build();
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

    @GetMapping("/likes")
    public ResponseEntity<?> getUserLikes(@AuthenticationPrincipal Users user) {
        Set<Products> product = this.productsService.findProductIsLike(user);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> likeProduct(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        Products product = this.productsService.likeProduct(id, user);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<?> dislikeProduct(@PathVariable Long id,
                                            @AuthenticationPrincipal Users user) {
        Products product = this.productsService.deleteFromProductsLikes(id, user);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/pies")
    public ResponseEntity<?> getPies() {
        Set<Products> pies = this.productsService.findAllPies();
        return ResponseEntity.ok(pies);
    }

    @GetMapping("/buns")
    public ResponseEntity<?> getBuns() {
        Set<Products> buns = this.productsService.findAllBuns();
        return ResponseEntity.ok(buns);
    }

    @GetMapping("/sweets")
    public ResponseEntity<?> getSweets() {
        Set<Products> sweets = this.productsService.findAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/cakes")
    public ResponseEntity<?> getCakes() {
        Set<Products> cakes = this.productsService.findAllCakes();
        return ResponseEntity.ok(cakes);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getSearchProducts(@RequestBody CategoryProductDto categoryProductDto) {
        Set<Products> searchedProducts = this.productsService.findSearchedProducts(categoryProductDto);
        return ResponseEntity.ok(searchedProducts);
    }

    @GetMapping("")
    public ResponseEntity<?> getProduct(@AuthenticationPrincipal Users user) {
        Set<Products> productById = productsService.findByUser(user);
        for (Products current : productById) {
            for (Users userC : current.getUserLikes()) {
                userC.setAuthorities(null);
                userC.setPassword(null);
                userC.setFirstName(null);
                userC.setLastName(null);
                userC.setPhoneNumber(null);
                userC.setVerificationCode(null);
                userC.setEmail(null);
                userC.setAddress(null);
            }
        }
        productById.forEach(p -> p.getShops().setUsers(null));
        return ResponseEntity.ok(productById);
    }
}
