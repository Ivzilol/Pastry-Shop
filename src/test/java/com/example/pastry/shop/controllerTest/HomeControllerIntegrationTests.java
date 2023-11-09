package com.example.pastry.shop.controllerTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;
    private static TestRestTemplate restTemplate;
    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/");
    }

    @Test
    public void testGetMostOrderedProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                        .value("Праскови"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name")
                        .value("Плато сладки"))
                .andReturn();
    }

    @Test
    public void testGetRecommendedProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/home"))
                .andExpect(status().isOk());
    }
}
