package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.testRepository.TestH2RepositoryShops;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryShops testH2RepositoryShops;

    @BeforeAll
    public static void init() {
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

    @Test
    public void testGetShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testShopById() throws Exception {
        Long shopId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{shopId}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateShop() throws Exception {
        Long shopId = 1L;
        Shops shop  = new Shops();
        shop.setName("Test");
        shop.setTown("TestTown");
        shop.setAddress("test");
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/{shopId}", shopId, shop))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails("Tosho")
    public void deleteShop() throws Exception {
        List<Shops> shops = testH2RepositoryShops.findAll();
        Long shopId = shops.stream().findFirst().get().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/delete/{id}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
