package com.example.pastry.shop.controllerTest;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @Autowired
    private TestH2RepositoryAuthority testH2RepositoryAuthority;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/users");
    }


//    @Test
//    public void testCreateUser() {
//        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
//        registrationDto.setUsername("Tosho");
//        registrationDto.setFirstName("Georgi");
//        registrationDto.setLastName("Georgiev");
//        registrationDto.setEmail("gundi@abv.bg");
//        registrationDto.setAddress("Sofiq");
//        registrationDto.setPhoneNumber("0887778899");
//        registrationDto.setPassword("asdasd");
//        registrationDto.setConfirmPassword("asdasd");
//        Users response = restTemplate.postForObject(baseUrl + "/register", registrationDto, Users.class);
//        Assertions.assertEquals("Tosho", response.getUsername());
//        Assertions.assertEquals("gundi@abv.bg", response.getEmail());
//        Assertions.assertEquals("Georgi", response.getFirstName());
//        Assertions.assertEquals("Georgiev", response.getLastName());
//        Assertions.assertEquals("Sofiq", response.getAddress());
//        Assertions.assertEquals("0887778899", response.getPhoneNumber());
//        Assertions.assertEquals(1, testH2RepositoryUsers.findAll().size());
//    }
//
//    @Test
//    public void testCreateAdmin() {
//        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
//        registrationDto.setUsername("Tosho");
//        registrationDto.setFirstName("Georgi");
//        registrationDto.setLastName("Georgiev");
//        registrationDto.setEmail("gundi@abv.bg");
//        registrationDto.setAddress("Sofiq");
//        registrationDto.setPhoneNumber("0887778899");
//        registrationDto.setPassword("bbGGbb123");
//        registrationDto.setConfirmPassword("bbGGbb123");
//        Users response = restTemplate.postForObject(baseUrl + "/register", registrationDto, Users.class, Authority.class);
//        Assertions.assertEquals("Tosho", response.getUsername());
//        Assertions.assertEquals("gundi@abv.bg", response.getEmail());
//        Assertions.assertEquals("Georgi", response.getFirstName());
//        Assertions.assertEquals("Georgiev", response.getLastName());
//        Assertions.assertEquals("Sofiq", response.getAddress());
//        Assertions.assertEquals("0887778899", response.getPhoneNumber());
//        Assertions.assertEquals(1, testH2RepositoryUsers.findAll().size());
//        Assertions.assertEquals(1, testH2RepositoryAuthority.findAll().size());
//    }

    @Test
    public void testGetAllUsers() {
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
        Assertions.assertEquals(2, testH2RepositoryUsers.findAll().size());
        Assertions.assertEquals(2, testH2RepositoryAuthority.findAll().size());
        Assertions.assertEquals("Sofiq", response.getAddress());
        Assertions.assertEquals("Sofiq", response2.getAddress());
        List<Authority> admins = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("admin")).toList();
        List<Authority> users = testH2RepositoryAuthority.findAll()
                .stream().filter(a -> a.getAuthority().equals("user")).toList();
        Assertions.assertEquals(1, admins.size());
        Assertions.assertEquals(1, users.size());
        List<Users> allUsers = testH2RepositoryUsers.findAll();
        Assertions.assertEquals(2, allUsers.size());
        Users userById = testH2RepositoryUsers.findAll().stream()
                .filter(u -> u.getUsername().equals("Tosho")).findFirst().orElse(null);
        Assertions.assertEquals("Tosho", userById.getUsername());

    }
}
