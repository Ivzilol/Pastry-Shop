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
        Users userForFind = testH2RepositoryUsers.findAll()
                .stream().filter(u -> u.getUsername().equals("Victor")).findFirst().orElse(null);
        Users currentUser = restTemplate.getForObject(baseUrl, Users.class, userForFind);
        Assertions.assertEquals(response, currentUser);

    }

//    @Test
//    public void testRepoSize() {
//        Assertions.assertEquals(2, testH2RepositoryUsers.findAll().size());
//        Assertions.assertEquals(2, testH2RepositoryAuthority.findAll().size());
//    }
//
//    @Test
//    public void testTypeUser() {
//        List<Authority> admins = testH2RepositoryAuthority.findAll()
//                .stream().filter(a -> a.getAuthority().equals("admin")).toList();
//        List<Authority> users = testH2RepositoryAuthority.findAll()
//                .stream().filter(a -> a.getAuthority().equals("user")).toList();
//        Assertions.assertEquals(1, admins.size());
//        Assertions.assertEquals(1, users.size());
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<Users> allUsers = testH2RepositoryUsers.findAll();
//        Assertions.assertEquals(2, allUsers.size());
//    }

    @Test
    public void testGetUserByUsername() {
        Users userById = testH2RepositoryUsers.findAll().stream()
                .filter(u -> u.getUsername().equals("Tosho")).findFirst().orElse(null);
        Assertions.assertEquals("Tosho", userById.getUsername());
    }
}
