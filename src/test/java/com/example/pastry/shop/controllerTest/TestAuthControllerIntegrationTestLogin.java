//package com.example.pastry.shop.controllerTest;
//
//import com.example.pastry.shop.model.entity.Users;
//import com.example.pastry.shop.repository.UsersRepository;
//import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TestAuthControllerIntegrationTestLogin {
//
//    @LocalServerPort
//    private int port;
//
//    private String baseUrl = "http://localhost";
//
//    private static RestTemplate restTemplate;
//
//    @Autowired
//    private TestH2RepositoryUsers testH2RepositoryUsers;
//
//
//
//    @BeforeAll
//    public static void init() {
//        restTemplate = new RestTemplate();
//    }
//
//    @BeforeEach
//    public void setUpUserUrl() {
//        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/users/admin");
//    }
//
//
//
//
//
//
//}
