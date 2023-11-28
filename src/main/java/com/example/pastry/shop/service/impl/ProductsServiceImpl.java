package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import com.example.pastry.shop.service.ProductsService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.pastry.shop.common.ExceptionMessages.*;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductRepository productRepository;

    private final ShopsRepository shopsRepository;

    private final CloudinaryServiceImpl cloudinaryService;

    private final MonitoringServiceImpl monitoringService;

    @Value("${category_p}")
    String category_p;

    @Value("${category_b}")
    String category_b;

    @Value("${category_s}")
    String category_s;

    @Value("${category_c}")
    String category_c;

    public ProductsServiceImpl(ProductRepository productRepository, ShopsRepository shopsRepository, CloudinaryServiceImpl cloudinaryService, MonitoringServiceImpl monitoringService) {
        this.productRepository = productRepository;
        this.shopsRepository = shopsRepository;
        this.cloudinaryService = cloudinaryService;
        this.monitoringService = monitoringService;
    }

    @Override
    public Products createProduct(CreateProductDTO productDTO, MultipartFile file) throws IOException {
        Products newProduct = new Products();
        newProduct.setName(productDTO.getName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setCategories(productDTO.getCategories());
        newProduct.setDescription(productDTO.getDescription());
        Shops shop = this.shopsRepository.findByName(productDTO.getShopName());
        newProduct.setShops(shop);
        newProduct.setImageUrl(getImage(file));
        this.productRepository.save(newProduct);
        return newProduct;
    }

    private String getImage(MultipartFile file) throws IOException {
        String pictureUrl = "";
        if (file != null) {
            pictureUrl = this.cloudinaryService.uploadPicture(file);
        }
        return pictureUrl;
    }

    @Override
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

    @Override
    public Optional<GetProductsDTO> findById(Long productId) {
        return productRepository.findOrderProductById(productId);
    }

    @Override
    public boolean saveProduct(UpdateProductDTO updateProductDTO, Long productId, MultipartFile file) throws IOException {
        Products updateProduct = this.productRepository.findProductById(productId);
        updateProduct.setName(updateProductDTO.getName());
        updateProduct.setPrice(updateProductDTO.getPrice());
        updateProduct.setCategories(updateProductDTO.getCategories());
        updateProduct.setDescription(updateProductDTO.getDescription());
        if (file != null) {
            updateProduct.setImageUrl(getImage(file));
        }
        this.productRepository.save(updateProduct);
        return true;
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Optional<Products> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Products likeProduct(Long id, Users user) {
        Products product = this.productRepository.findProductById(id);
        Products product1 = getProducts(user, product);
        if (product1 != null) return product1;
        return product;
    }

    @Nullable
    private Products getProducts(Users user, Products product) {
        boolean isUser = isUser(user);
        Set<Users> userLikes = product.getUserLikes();
        boolean likeUsers = isLike(user, userLikes);
        if (isUser && !likeUsers) {
            product.getUserLikes().add(user);
            product.setLikes(product.getLikes() + 1);
            this.productRepository.save(product);
            return product;
        }
        return null;
    }

    @Override
    public void deleteFromProductsLikes(Long id, Users user) {
        Products product = this.productRepository.findProductById(id);
        Products product1 = getProductsNotLike(user, product);
        if (product1 != null) {
        }
    }

    @Nullable
    private Products getProductsNotLike(Users user, Products product) {
        boolean isUser = isUser(user);
        Set<Users> userLikes = product.getUserLikes();
        Set<Users> newUserLikes = userLikes
                .stream()
                .filter(u -> !u.getUsername().equals(user.getUsername()))
                .collect(Collectors.toSet());
        user.getLikeProducts().remove(product);
        boolean likeUser = isLike(user, userLikes);
        if (isUser && likeUser) {
            product.setUserLikes(newUserLikes);
            product.setLikes(product.getLikes() - 1);
            this.productRepository.save(product);
            return product;
        }
        return null;
    }

    private boolean isLike(Users user, Set<Users> userLikes) {
        boolean isTrue = false;
        isTrue = findIsLike(user, userLikes, isTrue);
        return isTrue;
    }

    private static boolean findIsLike(Users user, Set<Users> userLikes, boolean isTrue) {
        for (Users currentUser : userLikes) {
            if (Objects.equals(currentUser.getId(), user.getId())) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    @Override
    @Cacheable("pie")
    public Set<GetProductsDTO> findAllPies() {
        return this.productRepository.findAllPies(category_p);
    }

    @Override
    @Cacheable("buns")
    public Set<GetProductsDTO> findAllBuns() {
        return this.productRepository.findAllBuns(category_b);
    }

    @Override
    @Cacheable("sweets")
    public Set<GetProductsDTO> findAllSweets() {
        return this.productRepository.findAllSweets(category_s);
    }

    @Override
    @Cacheable("cakes")
    public Set<GetProductsDTO> findAllCakes() {
        return this.productRepository.findAllCakes(category_c);
    }

    @Override
    public Set<GetProductsDTO> findSearchedProducts(CategoryProductDto categoryProductDto) {
        monitoringService.logProductSearch();
        return switch (categoryProductDto.getSelectOptions()) {
            case "pie" -> findAllPies();
            case "buns" -> findAllBuns();
            case "sweets" -> findAllSweets();
            case "cake" -> findAllCakes();
            default -> null;
        };
    }

    @Override
    public List<ProductsDTO> getProductsForDTO(Users user) {
        return getProductsDTOS(user);
    }

    @NotNull
    public List<ProductsDTO> getProductsDTOS(Users user) {
        Set<Products> products = this.productRepository.findByUser(user);
        List<ProductsDTO> returnDTO = new ArrayList<>();
        for (Products product : products) {
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setId(product.getId());
            productsDTO.setCategories(product.getCategories());
            productsDTO.setDescription(product.getDescription());
            productsDTO.setImageUrl(product.getImageUrl());
            productsDTO.setLikes(product.getLikes());
            productsDTO.setName(product.getName());
            productsDTO.setPrice(product.getPrice());
            Set<Users> users = new HashSet<>(product.getUserLikes());
            Set<UsersLikesDTO> usersLikesDTOS = new HashSet<>();
            users.forEach(u -> {
                UsersLikesDTO dto = new UsersLikesDTO();
                dto.setId(u.getId());
                dto.setUsername(u.getUsername());
                usersLikesDTOS.add(dto);
            });
            productsDTO.setUserLikes(usersLikesDTOS);
            returnDTO.add(productsDTO);
        }
        return returnDTO;
    }

    @Override
    public void setErrors(List<String> errors, ErrorProductDTO errorProductDTO) {
        for (String error : errors) {
            switch (error) {
                case EMPTY_PRODUCT -> errorProductDTO.setNameError(EMPTY_PRODUCT);
                case PRODUCT_EXIST -> errorProductDTO.setNameError(PRODUCT_EXIST);
                case NEGATIVE_NUMBER_PRICE -> errorProductDTO.setPriceError(NEGATIVE_NUMBER_PRICE);
                case EMPTY_CATEGORY -> errorProductDTO.setCategoriesError(EMPTY_CATEGORY);
                case EMPTY_DESCRIPTION -> errorProductDTO.setDescriptionError(EMPTY_DESCRIPTION);
                case EMPTY_SHOP_NAME -> errorProductDTO.setShopError(EMPTY_SHOP_NAME);
            }
        }
    }
}
