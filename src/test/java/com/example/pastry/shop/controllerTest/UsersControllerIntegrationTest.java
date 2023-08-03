package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.UpdateUserDTO;
import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.testRepository.TestH2RepositoryAuthority;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsersControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    private static TestRestTemplate restTemplate;


    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @Autowired
    private TestH2RepositoryAuthority testH2RepositoryAuthority;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/users");
    }


    @Test
    public void testRegisterUsers() {
        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername("Tosho");
        registrationDto.setFirstName("Georgi");
        registrationDto.setLastName("Georgiev");
        registrationDto.setEmail("gundi@abv.bg");
        registrationDto.setAddress("Sofiq");
        registrationDto.setPhoneNumber("0887778899");
        registrationDto.setPassword("bbGGbb123");
        registrationDto.setConfirmPassword("bbGGbb123");
        Users response = restTemplate.postForObject(baseUrl + "/register", registrationDto, Users.class);
        UserRegistrationDTO registrationDto2 = new UserRegistrationDTO();
        registrationDto2.setUsername("Victor");
        registrationDto2.setFirstName("Victor");
        registrationDto2.setLastName("Victorov");
        registrationDto2.setEmail("victor@abv.bg");
        registrationDto2.setAddress("Sofiq");
        registrationDto2.setPhoneNumber("0898776655");
        registrationDto2.setPassword("asdasd");
        registrationDto2.setConfirmPassword("asdasd");
        Users response2 = restTemplate.postForObject(baseUrl + "/register", registrationDto2, Users.class);
        Assertions.assertEquals("Tosho", response.getUsername());
        Assertions.assertEquals("Victor", response2.getUsername());
        Assertions.assertEquals(2, testH2RepositoryUsers.findAll().size());
        Assertions.assertEquals(2, testH2RepositoryAuthority.findAll().size());
        List<Authority> admins = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("admin")).toList();
        List<Authority> users = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("user")).toList();
        Assertions.assertEquals(1, admins.size());
        Assertions.assertEquals(1, users.size());
        List<Users> allUsers = testH2RepositoryUsers.findAll();
        Assertions.assertEquals(2, allUsers.size());
        Long currentId = 1L;
        Users byId = restTemplate.getForObject(baseUrl + "/{id}", Users.class, currentId);
        Assertions.assertEquals("Tosho", byId.getUsername());
    }

    @Test
    @WithUserDetails("Tosho")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin"))
                .andExpect(status().isOk());
        List<Users> allUsers = testH2RepositoryUsers.findAll();
        Assertions.assertEquals(2, allUsers.size());
    }

    @Test
    @WithUserDetails("Victor")
    public void getCurrentUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk());
        Optional<Users> user = testH2RepositoryUsers.findById(2L);
        Assertions.assertEquals("Victor", user.get().getUsername());
    }

    @Test
    public void testGetUserById() throws Exception {
        Long id = 2L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", id))
                .andExpect(status().isOk());
        Optional<Users> user = testH2RepositoryUsers.findById(id);
        Assertions.assertEquals("Victor", user.get().getUsername());
    }

    @Test
    @WithUserDetails("Victor")
    public void updateUser() throws Exception {
        Long userId = 1L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Victor");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("victor@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        Users user = restTemplate.patchForObject(baseUrl + "/edit/{id}", updateUserDTO, Users.class, userId);
        Assertions.assertEquals("Sofiq", user.getAddress());
    }
}
