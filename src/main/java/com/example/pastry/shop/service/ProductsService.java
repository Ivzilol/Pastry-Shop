package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.CategoryProductDto;
import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.dto.GetProductsDTO;
import com.example.pastry.shop.model.dto.UpdateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.ProductRepository;
import com.example.pastry.shop.repository.ShopsRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private final ProductRepository productRepository;

    private final ShopsRepository shopsRepository;

    private final CloudinaryService cloudinaryService;

    public ProductsService(ProductRepository productRepository, ShopsRepository shopsRepository, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.shopsRepository = shopsRepository;
        this.cloudinaryService = cloudinaryService;
    }

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

    public Optional<GetProductsDTO> findById(Long productId) {
        return productRepository.findOrderProductById(productId);
    }

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

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Optional<Products> findByName(String name) {
        return productRepository.findByName(name);
    }

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

    public Products deleteFromProductsLikes(Long id, Users user) {
        Products product = this.productRepository.findProductById(id);
        Products product1 = getProductsNotLike(user, product);
        if (product1 != null) return product1;
        return product;
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

    public Set<GetProductsDTO> findAllPies() {
        return this.productRepository.findAllPies();
    }

    public Set<GetProductsDTO> findAllBuns() {
        return this.productRepository.findAllBuns();
    }

    public Set<GetProductsDTO> findAllSweets() {
        return this.productRepository.findAllSweets();
    }

    public Set<GetProductsDTO> findAllCakes() {
        return this.productRepository.findAllCakes();
    }

    public Set<GetProductsDTO> findSearchedProducts(CategoryProductDto categoryProductDto) {
        return this.productRepository.findByCategories(categoryProductDto.getSelectOptions());
    }
}
