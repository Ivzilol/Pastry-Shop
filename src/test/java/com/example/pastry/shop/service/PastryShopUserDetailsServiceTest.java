package com.example.pastry.shop.service;

import com.example.pastry.shop.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PastryShopUserDetailsServiceTest {

    @Mock
    private UsersRepository mockUserRepo;

    private UserDetailsServiceImpl toTest;

    @BeforeEach
    void setUp(){
        toTest = new UserDetailsServiceImpl(
            mockUserRepo
        );
    }
}
