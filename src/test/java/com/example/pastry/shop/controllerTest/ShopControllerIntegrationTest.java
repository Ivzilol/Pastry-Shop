package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.testRepository.TestH2RepositoryShops;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    private String registerBaseUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryShops testH2RepositoryShops;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;


    @Value("${admin_password}")
    private String adminPassword;
    

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }


    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/shops");
        registerBaseUrl = registerBaseUrl.concat(":").concat(port + "").concat("/api/users");
    }

    @Test
    @Order(1)
    public void createUsers() {
        if (this.testH2RepositoryUsers.count() == 0) {
            UserRegistrationDTO registrationDto = new UserRegistrationDTO();
            registrationDto.setUsername("Tosho");
            registrationDto.setFirstName("Georgi");
            registrationDto.setLastName("Georgiev");
            registrationDto.setEmail("gundi@abv.bg");
            registrationDto.setAddress("Sofiq");
            registrationDto.setPhoneNumber("0887778899");
            registrationDto.setPassword(adminPassword);
            registrationDto.setConfirmPassword(adminPassword);
            Users response = restTemplate.postForObject(registerBaseUrl + "/register", registrationDto, Users.class);
            UserRegistrationDTO registrationDto2 = new UserRegistrationDTO();
            registrationDto2.setUsername("Victor");
            registrationDto2.setFirstName("Victor");
            registrationDto2.setLastName("Victorov");
            registrationDto2.setEmail("victor@abv.bg");
            registrationDto2.setAddress("Sofiq");
            registrationDto2.setPhoneNumber("0898776655");
            registrationDto2.setPassword("asdasd");
            registrationDto2.setConfirmPassword("asdasd");
            Users response2 = restTemplate.postForObject(registerBaseUrl + "/register", registrationDto2, Users.class);
            UserRegistrationDTO registrationDto3 = new UserRegistrationDTO();
            registrationDto3.setUsername("Ivo");
            registrationDto3.setFirstName("Ivaylo");
            registrationDto3.setLastName("Alichkov");
            registrationDto3.setEmail("ivo@abv.bg");
            registrationDto3.setAddress("Sofiq");
            registrationDto3.setPhoneNumber("0898776655");
            registrationDto3.setPassword("asdasd");
            registrationDto3.setConfirmPassword("asdasd");
            Users response3 = restTemplate.postForObject(registerBaseUrl + "/register", registrationDto3, Users.class);
            Assertions.assertEquals("Tosho", response.getUsername());
            Assertions.assertEquals("Victor", response2.getUsername());
            Assertions.assertEquals("Ivo", response3.getUsername());
        }
    }

    @Test
    @Order(2)
    @WithUserDetails("Tosho")
    public void testCreateShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals(2, testH2RepositoryShops.count());
    }

    @Test
    @Order(3)
    public void testGetShop() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Sladcarnicata na Mama"))
                .andReturn();
    }

    @Test
    @Order(4)
    @WithUserDetails("Tosho")
    public void deleteShop() throws Exception {
        Long shopId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/delete/{id}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals(1, testH2RepositoryShops.count());
    }

    @Test
    @Order(5)
    public void testShopById() throws Exception {
        Long shopId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{shopId}", shopId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("Sladcarnicata na Mama"))
                .andReturn();
    }

    @Test
    @Order(6)
    @WithUserDetails("Tosho")
    public void testUpdateShop() throws Exception {
        List<Shops> shopId = this.testH2RepositoryShops.findAll();
        Long id = shopId.stream().findFirst().get().getId();
        Shops shop = new Shops();
        shop.setId(id);
        shop.setTown("Sofiq");
        shop.setAddress("str. AlaBala");
        shop.setName("Sladcarnicata na Mama");
        String jsonRequest = new ObjectMapper().writeValueAsString(shop);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/{shopId}", id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful update shop")).andReturn();

    }
}
