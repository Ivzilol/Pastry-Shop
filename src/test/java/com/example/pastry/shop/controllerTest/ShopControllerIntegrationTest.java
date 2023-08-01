package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.testRepository.TestH2RepositoryShops;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;



    @Autowired
    private TestH2RepositoryShops testH2RepositoryShops;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }


    public Users createUser() {
        Long userId = 1L;
        return this.testH2RepositoryUsers.findUser(userId);
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/shops");
    }

    @Test
    @WithUserDetails("Tosho")
    public void testCreateShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
