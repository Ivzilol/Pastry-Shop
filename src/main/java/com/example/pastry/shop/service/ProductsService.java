package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductsService {

    private final ProductRepository productRepository;

    private final ShopsRepository shopsRepository;

    public ProductsService(ProductRepository productRepository, ShopsRepository shopsRepository, UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.shopsRepository = shopsRepository;
    }

    public Products createProduct(CreateProductDTO productDTO) {
        Products newProduct = new Products();
        newProduct.setName(productDTO.getName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setCategories(productDTO.getCategories());
        newProduct.setImageUrl(productDTO.getImageUrl());
        Optional<Shops> shop = this.shopsRepository.findByName(productDTO.getShopName());
        newProduct.setShops(shop.get());
        productRepository.save(newProduct);
        return newProduct;
    }

    public Set<Products> findByUser(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return productRepository.findByAdmin(user);
        } else {
            return productRepository.findByUser(user);
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    private static boolean isUser(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.user.name().equals(auth.getAuthority()));
    }

    public Optional<Products> findById(Long productId) {
        return productRepository.findById(productId);
    }

    public Products saveProduct(Products product) {
        return this.productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Optional<Products> findByName(String name) {
        return productRepository.findByName(name);
    }

    public Products likeProduct(Long id, Users user) {
        Products product = this.productRepository.findProductById(id);
        boolean isUser = isUser(user);
        Set<Users> userLikes = product.getUserLikes();
        boolean likeUsers = isLike(user, userLikes);
        if (isUser && !likeUsers) {
            product.getUserLikes().add(user);
            product.setLikes(product.getLikes() + 1);
            this.productRepository.save(product);
            return product;
        }
        return product;
    }

    private boolean isLike(Users user, Set<Users> userLikes) {
        boolean isTrue = false;
        for (Users currentUser : userLikes) {
            if (Objects.equals(currentUser.getId(), user.getId())) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    public Set<Products> findProductIsLike(Users user) {
        return user.getLikeProducts();
    }

    public Set<Products> findAllPies() {
        return this.productRepository.findAllPies();
    }

    public Set<Products> findAllBuns() {
        return this.productRepository.findAllBuns();
    }

    public Set<Products> findAllSweets() {
        return this.productRepository.findAllSweets();
    }
}
