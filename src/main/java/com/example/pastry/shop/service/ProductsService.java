package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ProductsService {

    Products createProduct(CreateProductDTO productDTO, MultipartFile file) throws IOException;

    Set<Products> findByUser(Users user);

    Optional<GetProductsDTO> findById(Long productId);

    boolean saveProduct(UpdateProductDTO updateProductDTO, Long productId, MultipartFile file) throws IOException;

    void delete(Long productId);

    Optional<Products> findByName(String name);

    Products likeProduct(Long id, Users user);

    void deleteFromProductsLikes(Long id, Users user);

    Set<GetProductsDTO> findAllPies();

    Set<GetProductsDTO> findAllBuns();

    Set<GetProductsDTO> findAllSweets();

    Set<GetProductsDTO> findAllCakes();

    Set<GetProductsDTO> findSearchedProducts(CategoryProductDto categoryProductDto);

    List<ProductsDTO> getProductsForDTO(Users user);

    void setErrors(List<String> errors, ErrorProductDTO errorProductDTO);
}
