package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.UpdateUserDTO;
import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsersControllerIntegrationTest {

    //always start first test -> testRegisterUsers()
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
        if (this.testH2RepositoryUsers.count() == 0) {
            UserRegistrationDTO registrationDto = new UserRegistrationDTO();
            registrationDto.setUsername("Tosho");
            registrationDto.setFirstName("Georgi");
            registrationDto.setLastName("Georgiev");
            registrationDto.setEmail("gundi@abv.bg");
            registrationDto.setAddress("Sofiq");
            registrationDto.setPhoneNumber("0887778899");
            registrationDto.setPassword("Ivailo7325");
            registrationDto.setConfirmPassword("Ivailo7325");
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
            UserRegistrationDTO registrationDto3 = new UserRegistrationDTO();
            registrationDto3.setUsername("Ivo");
            registrationDto3.setFirstName("Ivaylo");
            registrationDto3.setLastName("Alichkov");
            registrationDto3.setEmail("ivo@abv.bg");
            registrationDto3.setAddress("Sofiq");
            registrationDto3.setPhoneNumber("0898776655");
            registrationDto3.setPassword("asdasd");
            registrationDto3.setConfirmPassword("asdasd");
            Users response3 = restTemplate.postForObject(baseUrl + "/register", registrationDto3, Users.class);
            Assertions.assertEquals("Tosho", response.getUsername());
            Assertions.assertEquals("Victor", response2.getUsername());
            Assertions.assertEquals("Ivo", response3.getUsername());
        }
    }

    //always start first test -> testRegisterUsers()
    @Test
    public void testRegisterUsers() {
        Assertions.assertEquals(3, testH2RepositoryUsers.findAll().size());
        Assertions.assertEquals(3, testH2RepositoryAuthority.findAll().size());
        List<Authority> admins = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("admin")).toList();
        List<Authority> users = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("user")).toList();
        Assertions.assertEquals(1, admins.size());
        Assertions.assertEquals(2, users.size());
        List<Users> allUsers = testH2RepositoryUsers.findAll();
        Assertions.assertEquals(3, allUsers.size());
        Long currentId = 1L;
        Users byId = restTemplate.getForObject(baseUrl + "/{id}", Users.class, currentId);
        Assertions.assertEquals("Tosho", byId.getUsername());
    }

    @Test
    @WithUserDetails("Tosho")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName")
                        .value("Victorov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email")
                        .value("victor@abv.bg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address")
                        .value("Sofiq"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username")
                        .value("Ivo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName")
                        .value("Ivaylo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName")
                        .value("Alichkov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email")
                        .value("ivo@abv.bg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address")
                        .value("Sofiq"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void getCurrentUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                        .value("Victorov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("victor@abv.bg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address")
                        .value("Sofiq"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber")
                        .value("0898776655"))
                .andReturn();
    }

    @Test
    public void testGetUserById() throws Exception {
        Long id = 2L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                        .value("Victor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName")
                        .value("Victorov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                        .value("victor@abv.bg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address")
                        .value("Sofiq"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber")
                        .value("0898776655"))
                .andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    public void updateUser() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Victor");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("victor@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}", updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Successful update user!");
    }

    @Test
    @WithUserDetails("Victor")
    public void testUnsuccessfulUpdateUser_HaveUserWithSameUsername() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Ivo");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("victor@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}", updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Unsuccessful update user!");
    }

    @Test
    @WithUserDetails("Victor")
    public void testUnsuccessfulUpdateUser_HaveUserWithSameEmail() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Victor");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("ivo@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}", updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Unsuccessful update user!");
    }
}
