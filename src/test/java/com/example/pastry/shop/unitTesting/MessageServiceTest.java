package com.example.pastry.shop.unitTesting;

import com.example.pastry.shop.model.dto.Message;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.ChatMessagesRepository;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.service.MessageService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MessageServiceTest {

    private static UsersRepository mockUserRepository;
    private static ChatMessagesRepository mockChatMessageRepository;
    private static AuthorityRepository mockAuthorityRepository;

    private static Users testUsers;
    private static Authority testAuthority;
    private static Users testUserAdmin;
    private static Authority testAuthorityAdmin;

    private static MessageService testMessageService;

    public MessageServiceTest() {
    }


    @BeforeAll
    static void setUp() {
        mockUserRepository = mock(UsersRepository.class);
        mockChatMessageRepository = mock(ChatMessagesRepository.class);
        mockAuthorityRepository = mock(AuthorityRepository.class);
        testMessageService = new MessageService(mockChatMessageRepository, mockUserRepository);

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

        when(mockUserRepository.findByUserId(1L)).thenReturn(testUsers);
        when(mockUserRepository.findByUserId(2L)).thenReturn(testUserAdmin);

    }

    @Test
    public void testSaveUserMessage() {
        when(mockUserRepository.findByUsername("Victor")).thenReturn(Optional.of(testUsers));
        Message testMessage = new Message();
        testMessage.setMessage("Test Message");
        testMessage.setSenderName("Victor");
        testMessageService.saveMessage(testMessage, testUsers.getUsername());
        verify(mockChatMessageRepository).save(any());
    }

}
