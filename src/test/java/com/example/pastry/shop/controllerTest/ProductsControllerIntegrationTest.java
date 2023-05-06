package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2RepositoryProducts testH2RepositoryProducts;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }
    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/products");
    }
    @Test
    public void testAddProduct() {
        Products product = new Products();
        product.setName("Баница");
        product.setPrice(12.32);
        product.setCategories("pie");
        product.setDescription("Ръчно приготвена домашна баница!");
        product.setImageUrl("https://i.imgupx.com/zPzpldZI/333679583_1370979030385235_8353098425062243540_n.jpg");
        Products response = restTemplate.postForObject(baseUrl, product, Products.class);
        Assertions.assertEquals("Баница", response.getName());
        Assertions.assertEquals(1, (long) testH2RepositoryProducts.findAll().size());
    }



}

