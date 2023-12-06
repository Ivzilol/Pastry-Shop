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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.pastry.shop.common.ExceptionMessages.*;
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
        if (testH2RepositoryProducts.count() == 0) {
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
            MockMultipartFile product4 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Плато сладки\", \"description\": \"Плато сладки\", \"categories\": \"sweets\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"18.88\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                            .file(product4))
                    .andExpect(status().isOk());
            MockMultipartFile product5 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Плато сладки\", \"description\": \"Плато сладки\", \"categories\": \"sweets\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"18.88\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                            .file(product5))
                    .andExpect(status().isOk());
            Assertions.assertEquals(5, testH2RepositoryProducts.count());
        }
    }

    @Test
    @Order(2)
    public void testGetProductBId() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description")
                        .value("Най Вкусната баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value("21.99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories")
                        .value("pie"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    @Order(3)
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
    @Order(4)
    public void testGetUserLikes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    @Order(5)
    public void testDislikeProduct() throws Exception {
        Long productId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/dislike/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Not Like"))
                .andReturn();
    }

    @Test
    @Order(6)
    public void testGetCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/pies"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                        .value("Козунак"))
                .andReturn();
    }

    @Test
    @Order(7)
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
                .andReturn();
    }

    @Test
    @Order(8)
    public void testDeleteProduct() throws Exception {
        Long productId = 5L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Product Delete"))
                .andReturn();
        Assertions.assertEquals(4, testH2RepositoryProducts.count());
    }

    @Test
    @Order(9)
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        MockMultipartFile dto = new MockMultipartFile(
                "dto",
                "",
                "application/json",
                "{\"name\": \"Баница\", \"description\": \"Най Вкусната баница\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"21.99\"}".getBytes());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart(baseUrl + "/edit/{productId}", productId)
                        .file(dto)
                        .with(request -> {
                            request.setMethod(HttpMethod.PATCH.name());
                            return request;
                        })
        ).andExpect(status().isOk());
    }

    @Test
    @Order(10)
    public void testGetCategoriesSweets() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/sweets"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Праскови"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                        .value("Плато сладки"))
                .andReturn();
    }

    @Test
    @Order(11)
    public void testUnsuccessfulCreateProduct() throws Exception {
        MockMultipartFile product = new MockMultipartFile("dto",
                "",
                "application/json",
                "{\"name\": \"\", \"description\": \"\", \"categories\": \"\", \"shopName\": \"\", \"price\": \"-1.0\"}".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
                        .file(product))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameError")
                        .value(EMPTY_PRODUCT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceError")
                        .value(NEGATIVE_NUMBER_PRICE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriesError")
                        .value(EMPTY_CATEGORY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descriptionError")
                        .value(EMPTY_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shopError")
                        .value(EMPTY_SHOP_NAME))
                .andReturn();
    }

    @Test
    @Order(12)
    public void testEmptyCategorySearch() throws Exception {
        CategoryProductDto categoryProductDto = new CategoryProductDto();
        categoryProductDto.setSelectOptions("");
        String jsonRequest = new ObjectMapper().writeValueAsString(categoryProductDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/search")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value(SELECT_CATEGORY));
    }
}
