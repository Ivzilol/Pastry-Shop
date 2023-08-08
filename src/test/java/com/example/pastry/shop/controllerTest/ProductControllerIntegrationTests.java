package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
import com.example.pastry.shop.testRepository.TestH2RepositoryShops;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryProducts testH2RepositoryProducts;

    @Autowired
    private TestH2RepositoryShops testH2RepositoryShops;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/products");
    }

    @Test
    @WithUserDetails("Tosho")
    public void testCreateProduct() {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setName("Баница");
        createProductDTO.setPrice(21.99);
        createProductDTO.setDescription("Най Вкусната баница");
        createProductDTO.setCategories("pie");
        createProductDTO.setImageUrl("https://i.ibb.co/QM6sBZh/333679583-1370979030385235-8353098425062243540-n.jpg");
        createProductDTO.setShopName("Sladcarnicata na Mama");
        Products result = restTemplate.postForObject(baseUrl + "/create/admin", createProductDTO, Products.class);
        Assertions.assertEquals("Баница", result.getName());
    }
}
