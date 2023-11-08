package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.testRepository.TestH2RepositoryShops;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryShops testH2RepositoryShops;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }


    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/shops");
    }

    @Test
    @WithUserDetails("Tosho")
    public void testCreateShop() throws Exception {
        if (this.testH2RepositoryShops.count() == 0) {
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Test
    public void testGetShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Sladcarnicata na Mama"))
                .andReturn();
    }

    @Test
    public void testShopById() throws Exception {
        Long shopId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{shopId}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("Sladcarnicata na Mama"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Tosho")
    public void testUpdateShop() throws Exception {
        Long shopId = 1L;
        Shops shop = new Shops();
        shop.setId(1L);
        shop.setTown("Sofiq");
        shop.setAddress("str. AlaBala");
        shop.setName("Sladcarnicata na Mama");
        String jsonRequest = new ObjectMapper().writeValueAsString(shop);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/{shopId}", shopId)
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful update shop")).andReturn();

    }

    @Test
    @WithUserDetails("Tosho")
    public void deleteShop() throws Exception {
        List<Shops> shops = testH2RepositoryShops.findAll();
        Long shopId = shops.stream().findFirst().get().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/delete/{id}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Shops> shopsSize = testH2RepositoryShops.findAll();
        Assertions.assertEquals(0, shopsSize.size());
    }
}
