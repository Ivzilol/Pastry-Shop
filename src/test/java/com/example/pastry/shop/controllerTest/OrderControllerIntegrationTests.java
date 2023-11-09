package com.example.pastry.shop.controllerTest;


import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.testRepository.TestH2RepositoryOrders;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    private String ordersProcessingUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryProducts testH2RepositoryProducts;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @Autowired
    private TestH2RepositoryOrders testH2RepositoryOrders;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/orders");
        ordersProcessingUrl = ordersProcessingUrl.concat(":").concat(port + "").concat("/api/orders-processing");
    }

    @Test
    @WithUserDetails("Victor")
    public void testCreateOrder() throws Exception {
        Long productId1 = 1L;
        Long productId2 = 2L;
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value("25.55"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Праскови"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value("31.0"))
                .andReturn();
        Assertions.assertEquals(2, this.testH2RepositoryOrders.count());
    }

    @Test
    @WithUserDetails("Victor")
    public void testGetOrderByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price")
                        .value("25.55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status")
                        .value("newOrder"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void testDeleteOrderByUser() throws Exception {
        Long id = 9L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Delete product"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void testUpdateStatusOrder() throws Exception {
        OrdersStatusDTO ordersStatusDTO = new OrdersStatusDTO();
        ordersStatusDTO.setStatus("confirmed");
        String jsonRequest = new ObjectMapper().writeValueAsString(ordersStatusDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Confirm order"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Tosho")
    public void testGetAllConfirmedOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status")
                        .value("confirmed"))
                .andReturn();
    }

    @Test
    public void testChangeOrderStatus_Send() throws Exception {
        Long orderId = 1L;
        OrderStatusSendAdmin orderStatusSendAdmin = new OrderStatusSendAdmin();
        orderStatusSendAdmin.setStatus("sent");
        orderStatusSendAdmin.setDateDelivery("2023-11-09");
        orderStatusSendAdmin.setTimeDelivery("16:00");
        String jsonRequest = new ObjectMapper().writeValueAsString(orderStatusSendAdmin);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/{id}", orderId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Order send"))
                .andReturn();
    }

    @Test
    public void testStartProcessingOrder() throws Exception {
        Long orderId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post(ordersProcessingUrl + "/admin/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful start processing order"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Tosho")
    public void testGetAllSendOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin/send"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].statusOrder")
                        .value("sent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice")
                        .value("20.44"))
                .andReturn();
    }

    @Test
    public void testUpdateStatusDelivery() throws Exception {
        Long orderId = 1L;
        OrderStatusDeliveryAdmin orderStatusDeliveryAdmin = new OrderStatusDeliveryAdmin();
        orderStatusDeliveryAdmin.setStatus("delivery");
        String jsonRequest = new ObjectMapper().writeValueAsString(orderStatusDeliveryAdmin);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/admin/delivery/{id}", orderId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Order delivery"))
                .andReturn();
    }
}
