package com.example.pastry.shop.controllerTest;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

//    @Test
//    @WithUserDetails("Tosho")
//    public void testCreateProduct() throws Exception {
//        File file = new File("D:\\Img-pastry-shop\\333679583_1370979030385235_8353098425062243540_n");
//        MockMultipartFile imageUrl = new MockMultipartFile("imageUrl",
////                "333679583_1370979030385235_8353098425062243540_n",
////                "image/jpeg",
//                "333679583_1370979030385235_8353098425062243540_n.jpg".getBytes(StandardCharsets.UTF_8)
//                );
//
//        MockMultipartFile jsonFile = new MockMultipartFile("dto",
//                "",
//                "application/json",
//                "{\"name\": \"Баница\", \"description\": \"Най Вкусната баница\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"21.99\"}".getBytes());
////        String jsonRequest = new ObjectMapper().writeValueAsString(dto);
//        mockMvc.perform(MockMvcRequestBuilders.multipart(baseUrl + "/create/admin")
//                        .file(imageUrl)
//                        .file(jsonFile))
//                .andExpect(status().isOk());
//    }

    @Test
    @WithUserDetails("Victor")
    public void testGetProductByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk());
        List<Products> allProducts = testH2RepositoryProducts.findAll();
        Assertions.assertEquals(1, allProducts.size());
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

//    @Test
//    public void testUpdateProduct() {
//        Long productId = 1L;
//        Products product = testH2RepositoryProducts.findProductById(productId);
//        product.setName("New Name");
//        Products result = restTemplate.patchForObject(baseUrl + "/{productId}", product, Products.class, productId);
//        Assertions.assertEquals("New Name", result.getName());
//    }

    @Test
    @WithUserDetails("Victor")
    public void testGetUserLikes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/likes"))
                .andExpect(status().isOk());
        Long userId = 2L;
        Optional<Users> user = testH2RepositoryUsers.findById(userId);
        Set<Products> productLikes = user.get().getLikeProducts();
        Assertions.assertEquals(0, productLikes.size());
    }

    @Test
    public void testGetPies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/pies"))
                .andExpect(status().isOk());
        Set<Products> pies = testH2RepositoryProducts.findAllPies();
        Assertions.assertEquals(1, pies.size());
    }

    @Test
    public void testGetSweets() {

    }
}
