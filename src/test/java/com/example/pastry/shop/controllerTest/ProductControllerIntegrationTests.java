package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.CreateProductDTO;
import com.example.pastry.shop.model.entity.Products;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private TestH2RepositoryUsers testH2RepositoryUsers;

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

    @Test
    @WithUserDetails("Victor")
    public void testGetProductByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Products> allProducts = testH2RepositoryProducts.findAll();
        Assertions.assertEquals(1, allProducts.size());
    }

    @Test
    public void testGetProductBId() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{productId}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Optional<Products> productById = testH2RepositoryProducts.findById(productId);
        Assertions.assertEquals("Баница", productById.get().getName());
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Products product = testH2RepositoryProducts.findProductById(productId);
        product.setName("New Name");
        Products result = restTemplate.patchForObject(baseUrl + "/{productId}", product, Products.class, productId);
        Assertions.assertEquals("New Name", result.getName());
    }

    @Test
    @WithUserDetails("Victor")
    public void testGetUserLikes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/likes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Long userId = 2L;
        Optional<Users> user = testH2RepositoryUsers.findById(userId);
        Set<Products> productLikes = user.get().getLikeProducts();
        Assertions.assertEquals(0, productLikes.size());
    }
}
