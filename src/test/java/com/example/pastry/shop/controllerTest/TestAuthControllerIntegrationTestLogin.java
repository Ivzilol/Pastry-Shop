package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class TestAuthControllerIntegrationTestLogin {

    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @BeforeAll
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        setupUser();
    }

    private void setupUser() {
        Users user = new Users();
        user.setUsername("Tosho");
        user.setPassword("asdasd");
        user.setFirstName("Tosho");
        user.setLastName("Toshev");
        user.setEmail("tosho@abv.bg");
        user.setAddress("Sofiq");
        user.setPhoneNumber("0909887766");
        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUsers(user);
        user.setAuthorities(List.of(authority));
        this.usersRepository.save(user);
    }
    @Test
    @WithUserDetails("Tosho")
    public void getUserFromContext() throws Exception {
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tosho"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Tosho"))
                .andDo(print());
    }
}
