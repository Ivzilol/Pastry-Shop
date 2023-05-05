package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class PastryShopUserDetailsServiceTest {

    @Mock
    private UsersRepository mockUserRepo;


    @Test
    void testLoadUserByUsername_UserExist() {

        Optional<Users> testUsers = Optional.of(new Users());
        testUsers.get().setUsername("Gosho");
        testUsers.get().setFirstName("Georgi");
        testUsers.get().setLastName("Georgiev");
        testUsers.get().setEmail("Gosho@example.com");
        testUsers.get().setAddress("Sofiq");
        testUsers.get().setPhoneNumber("0887778899");
        testUsers.get().setPassword("asdasd");
        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUsers(testUsers.get());
        testUsers.get().setAuthorities(List.of(authority));
        doReturn(testUsers).when(mockUserRepo).findByUsername(testUsers.get().getUsername());
        UserDetailsServiceImpl userDetails = new UserDetailsServiceImpl(mockUserRepo);
        userDetails.loadUserByUsername(testUsers.get().getUsername());
        Assertions.assertEquals(testUsers.get().getUsername(),
                userDetails.loadUserByUsername("Gosho").getUsername());
        Assertions.assertEquals(testUsers.get().getAuthorities(),
                userDetails.loadUserByUsername("Gosho").getAuthorities());
    }

    @Test
    void testLoadUserByUsername_UserDoseNotExist() {
        Optional<Users> testUsers = Optional.of(new Users());
        testUsers.get().setUsername("Gosho");
        testUsers.get().setFirstName("Georgi");
        testUsers.get().setLastName("Georgiev");
        testUsers.get().setEmail("Gosho@example.com");
        testUsers.get().setAddress("Sofiq");
        testUsers.get().setPhoneNumber("0887778899");
        testUsers.get().setPassword("asdasd");
        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUsers(testUsers.get());
        testUsers.get().setAuthorities(List.of(authority));
        doReturn(testUsers).when(mockUserRepo).findByUsername(testUsers.get().getUsername());
        UserDetailsServiceImpl userDetails = new UserDetailsServiceImpl(mockUserRepo);
        userDetails.loadUserByUsername(testUsers.get().getUsername());
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetails.loadUserByUsername("Troicho").getUsername());
    }
}
