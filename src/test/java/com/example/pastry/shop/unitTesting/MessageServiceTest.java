package com.example.pastry.shop.unitTesting;

import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import com.example.pastry.shop.repository.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MessageServiceTest {

    @Mock
    private UsersRepository mockUserRepository;

    @Mock
    private ChatMessagesRepository mockChatMessageRepository;

    @Mock
    private AuthorityRepository mockAuthorityRepository;

    private Users testUsers;

    private Authority testAuthority;

    private Users testUserAdmin;

    private Authority testAuthorityAdmin;


    @BeforeEach
    void setUp() {
        testUsers = new Users();
        testUsers.setId(1L);
        testUsers.setUsername("Victor");
        testUsers.setFirstName("Victor");
        testUsers.setLastName("Victorov");
        testUsers.setEmail("victor@abv.bg");
        testUsers.setAddress("Sofiq");
        testUsers.setPhoneNumber("0898776655");
        testUsers.setPassword("123456");
        testUsers.setValidate(true);
        testUsers.setVerificationCode(RandomString.make(64));
        testAuthority = new Authority();
        testAuthority.setId(1L);
        testAuthority.setAuthority("user");
        testAuthority.setUsers(testUsers);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(testAuthority);
        testUsers.setAuthorities(authorities);

        testUserAdmin = new Users();
        testUserAdmin.setId(2L);
        testUserAdmin.setUsername("Admin");
        testUserAdmin.setFirstName("Admin");
        testUserAdmin.setLastName("Adminov");
        testUserAdmin.setEmail("admin@abv.bg");
        testUserAdmin.setAddress("Sofiq");
        testUserAdmin.setPhoneNumber("0989998877");
        testUserAdmin.setPassword("654321");
        testUserAdmin.setValidate(true);
        testUserAdmin.setVerificationCode(RandomString.make(64));
        testAuthorityAdmin = new Authority();
        testAuthorityAdmin.setId(2L);
        testAuthorityAdmin.setAuthority("admin");
        testAuthorityAdmin.setUsers(testUserAdmin);
        List<Authority> authoritiesAdmin = new ArrayList<>();
        authoritiesAdmin.add(testAuthorityAdmin);
        testUserAdmin.setAuthorities(authoritiesAdmin);

    }

    @Test
    public void testMessage() {
        System.out.println();
    }

}
