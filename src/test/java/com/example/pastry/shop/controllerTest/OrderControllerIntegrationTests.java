package com.example.pastry.shop.controllerTest;


import com.example.pastry.shop.model.dto.OrderStatusDeliveryAdmin;
import com.example.pastry.shop.model.dto.OrderStatusSendAdmin;
import com.example.pastry.shop.model.dto.OrdersStatusDTO;
import com.example.pastry.shop.testRepository.TestH2RepositoryOrders;
import com.example.pastry.shop.testRepository.TestH2RepositoryProducts;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    private String ordersProcessingUrl = "http://localhost";

    private String productsBaseUrl = "http://localhost";
    @Autowired
    private TestH2RepositoryProducts testH2RepositoryProducts;

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
        productsBaseUrl = productsBaseUrl.concat(":").concat(port + "").concat("/api/products");
    }

    @Test
    @Order(1)
    @WithUserDetails("Tosho")
    public void products() throws Exception {
        if (testH2RepositoryProducts.count() == 0) {
            MockMultipartFile product1 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Баница\", \"description\": \"Най Вкусната баница\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"21.99\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(productsBaseUrl + "/create/admin")
                            .file(product1))
                    .andExpect(status().isOk());
            MockMultipartFile product2 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Праскови\", \"description\": \"Най вкусните сладки\", \"categories\": \"sweets\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"31\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(productsBaseUrl + "/create/admin")
                            .file(product2))
                    .andExpect(status().isOk());
            MockMultipartFile product3 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Козунак\", \"description\": \"Козуначен хляб\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"20.21\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(productsBaseUrl + "/create/admin")
                            .file(product3))
                    .andExpect(status().isOk());
            MockMultipartFile product4 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Плато сладки\", \"description\": \"Плато сладки\", \"categories\": \"sweets\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"18.88\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(productsBaseUrl + "/create/admin")
                            .file(product4))
                    .andExpect(status().isOk());
            MockMultipartFile product5 = new MockMultipartFile("dto",
                    "",
                    "application/json",
                    "{\"name\": \"Погача\", \"description\": \"Погача от хрупкаво тесто\", \"categories\": \"pie\", \"shopName\": \"Sladcarnicata na Mama\", \"price\": \"14.44\"}".getBytes());
            mockMvc.perform(MockMvcRequestBuilders.multipart(productsBaseUrl + "/create/admin")
                            .file(product5))
                    .andExpect(status().isOk());
            Assertions.assertEquals(5, testH2RepositoryProducts.count());
        }
    }

    @Test
    @WithUserDetails("Victor")
    @Order(2)
    public void testCreateOrder() throws Exception {
        Long productId1 = 1L;
        Long productId2 = 2L;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour = currentDateTime.format(formatter);
        int intHour = Integer.parseInt(hour);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(intHour >= 8 && intHour < 21 ? "17.592" : "21.99"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Праскови"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(intHour >= 8 && intHour < 21 ? "24.8" : "31.0"))
                .andReturn();
        Assertions.assertEquals(2, this.testH2RepositoryOrders.count());
    }

    @Test
    @WithUserDetails("Ivo")
    @Order(3)
    public void testCreateOrderSecondUser() throws Exception {
        Long productId1 = 3L;
        Long productId2 = 4L;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour = currentDateTime.format(formatter);
        int intHour = Integer.parseInt(hour);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId1))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(intHour >= 8 && intHour < 21 ? "16.168" : "20.21"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/{id}", productId2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName")
                        .value("Плато сладки"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price")
                        .value(intHour >= 8 && intHour < 21 ? "15.104" : "18.88"))
                .andReturn();
        Assertions.assertEquals(4, this.testH2RepositoryOrders.count());
    }


    @Test
    @WithUserDetails("Ivo")
    @Order(4)
    public void testGetOrderByUser() throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour = currentDateTime.format(formatter);
        int intHour = Integer.parseInt(hour);
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price")
                        .value(intHour >= 8 && intHour < 21 ? "16.168" : "20.21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status")
                        .value("newOrder"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Ivo")
    @Order(5)
    public void testDeleteOrderByUser() throws Exception {
        Long id = 4L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Delete product"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Ivo")
    @Order(6)
    public void testUpdateStatusOrder() throws Exception {
        OrdersStatusDTO ordersStatusDTO = new OrdersStatusDTO();
        ordersStatusDTO.setStatus("confirmed");
        ordersStatusDTO.setPayment("");
        ordersStatusDTO.setPromoCode(null);
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
    @WithUserDetails("Ivo")
    @Order(7)
    public void testTrackingOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/tracking"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status")
                        .value("confirmed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Козунак"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Tosho")
    @Order(8)
    public void testGetAllConfirmedOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Козунак"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status")
                        .value("confirmed"))
                .andReturn();
    }

    @Test
    @Order(9)
    public void testChangeOrderStatus_Send() throws Exception {
        Long orderId = 1L;
        OrderStatusSendAdmin orderStatusSendAdmin = new OrderStatusSendAdmin();
        orderStatusSendAdmin.setStatus("sent");
        orderStatusSendAdmin.setDateDelivery("2025-12-31");
        orderStatusSendAdmin.setTimeDelivery("23:59");
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
    @Order(10)
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
    @Order(11)
    public void testGetAllSendOrders() throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String hour = currentDateTime.format(formatter);
        int intHour = Integer.parseInt(hour);
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin/send"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].statusOrder")
                        .value("sent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice")
                        .value(intHour >= 8 && intHour < 21 ? "16.168" : "20.21"))
                .andReturn();
    }

    @Test
    @Order(12)
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

    @Test
    @WithUserDetails("Ivo")
    @Order(13)
    public void testUserOrderHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/history/user"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username")
                        .value("Ivo"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Tosho")
    @Order(14)
    public void testAdminHistoryOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/history/admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username")
                        .value("Ivo"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    @Order(15)
    public void testNotConfirmedOrderByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/status"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName")
                        .value("Баница"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName")
                        .value("Праскови"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    @Order(16)
    public void testConfirmedOrdersByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/status/confirmed"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Victor")
    @Order(17)
    public void testNotSendOrdersAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/status/confirmed/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Tosho")
    @Order(18)
    public void testFindOrdersByDate() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        mockMvc.perform(MockMvcRequestBuilders.get(ordersProcessingUrl + "/admin/date")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk());
    }
}
