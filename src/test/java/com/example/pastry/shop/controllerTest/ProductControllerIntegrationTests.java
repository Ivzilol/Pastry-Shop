package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.CategoryProductDto;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    @WithUserDetails("Tosho")
    public void testCreateProduct() throws Exception {
        MockMultipartFile product1 = new MockMultipartFile("dto",
                "",
                "application/json",
                "{\"name\": \"Баница\", \"description\": \"Най Вкусната баница\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"21.99\"}".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                        .file(product1))
                .andExpect(status().isOk());
        MockMultipartFile product2 = new MockMultipartFile("dto",
                "",
                "application/json",
                "{\"name\": \"Праскови\", \"description\": \"Най вкусните сладки\", \"categories\": \"sweets\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"31\"}".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                        .file(product2))
                .andExpect(status().isOk());
        MockMultipartFile product3 = new MockMultipartFile("dto",
                "",
                "application/json",
                "{\"name\": \"Козунак\", \"description\": \"Козуначен хляб\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"20.21\"}".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                        .file(product3))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductBId() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value("Най вкусната Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value("25.55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl")
                        .value("http://res.cloudinary.com/dmxqvcevk/image/upload/v1699468575/wz1zplogpepazgdjdrs7.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories")
                        .value("pie"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void testLikeProduct() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Like"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void testGetUserLikes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void testDislikeProduct() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/dislike/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Not Like"))
                .andReturn();
    }

    @Test
    public void testGetCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/pies"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name")
                        .value("Погача"))
                .andReturn();
    }

    @Test
    public void testSearch() throws Exception {
        CategoryProductDto categoryProductDto = new CategoryProductDto();
        categoryProductDto.setSelectOptions("pie");
        String jsonRequest = new ObjectMapper().writeValueAsString(categoryProductDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/search")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name")
                        .value("Погача"))
                .andReturn();
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 9L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Product Delete"))
                .andReturn();
    }
}
