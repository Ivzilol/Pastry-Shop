package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.testRepository.TestH2RepositoryAuthority;
import com.example.pastry.shop.testRepository.TestH2RepositoryUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.example.pastry.shop.common.ExceptionMessages.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    private String authBaseURL = "http://localhost";

    private static TestRestTemplate restTemplate;

    @Autowired
    private TestH2RepositoryUsers testH2RepositoryUsers;

    @Autowired
    private TestH2RepositoryAuthority testH2RepositoryAuthority;

    @Value("${admin_password}")
    private String adminPassword;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/users");
        authBaseURL = authBaseURL.concat(":").concat(port + "").concat("/api/auth");
    }

    //always start first test -> testRegisterUsers() independently
    @Test
    @Order(1)
    public void testRegisterUsers() {
        if (this.testH2RepositoryUsers.count() == 0) {
            UserRegistrationDTO registrationDto = new UserRegistrationDTO();
            registrationDto.setUsername("Tosho");
            registrationDto.setFirstName("Georgi");
            registrationDto.setLastName("Georgiev");
            registrationDto.setEmail("gundi@abv.bg");
            registrationDto.setAddress("Sofiq");
            registrationDto.setPhoneNumber("0887778899");
            registrationDto.setPassword(adminPassword);
            registrationDto.setConfirmPassword(adminPassword);
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
    public void updateUser() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Victor");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("victor@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}",
                updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Successful update user!");
    }

    @Test
    @WithUserDetails("Victor")
    @Order(6)
    public void testUnsuccessfulUpdateUser_HaveUserWithSameUsername() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Ivo");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("victor@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}",
                updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Unsuccessful update user!");
    }

    @Test
    @WithUserDetails("Victor")
    @Order(7)
    public void testUnsuccessfulUpdateUser_HaveUserWithSameEmail() {
        Long userId = 2L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setUsername("Victor");
        updateUserDTO.setFirstName("Victor");
        updateUserDTO.setLastName("Victorov");
        updateUserDTO.setEmail("ivo@abv.bg");
        updateUserDTO.setAddress("Sofiq");
        updateUserDTO.setPhoneNumber("0898776655");
        CustomResponse customResponse = restTemplate.patchForObject(baseUrl + "/edit/{id}",
                updateUserDTO, CustomResponse.class, userId);
        Assertions.assertEquals(customResponse.getCustom(), "Unsuccessful update user!");
    }

    @Test
    @WithUserDetails("Victor")
    @Order(8)
    public void testChangePassword() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("asdasd");
        changePasswordDto.setNewPassword("asdasd");
        changePasswordDto.setConfirmNewPassword("asdasd");
        String jsonRequest = new ObjectMapper().writeValueAsString(changePasswordDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/change-password")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful change your password")).andReturn();
    }

    @Test
    @WithUserDetails("Victor")
    @Order(9)
    public void testUnsuccessfulChangePassword_PasswordNotMatch() throws Exception {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPassword("asdasd");
        changePasswordDto.setNewPassword("asdasd");
        changePasswordDto.setConfirmNewPassword("123456");
        String jsonRequest = new ObjectMapper().writeValueAsString(changePasswordDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/change-password")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Victor")
    @Order(10)
    public void testSendEmailForgottenPassword() throws Exception {
        ForgottenPasswordEmailDto forgottenPasswordEmailDto = new ForgottenPasswordEmailDto();
        forgottenPasswordEmailDto.setEmail("victor@abv.bg");
        String jsonRequest = new ObjectMapper().writeValueAsString(forgottenPasswordEmailDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register/forgotten-password")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(11)
    public void testRegUser() throws Exception {
        if (this.testH2RepositoryUsers.count() == 3) {
            UserRegistrationDTO registrationDto = new UserRegistrationDTO();
            registrationDto.setUsername("Pesho");
            registrationDto.setFirstName("Petur");
            registrationDto.setLastName("Petrov");
            registrationDto.setEmail("pesho@abv.bg");
            registrationDto.setAddress("Sofiq");
            registrationDto.setPhoneNumber("0898776655");
            registrationDto.setPassword("asdasd");
            registrationDto.setConfirmPassword("asdasd");
            String jsonRequest = new ObjectMapper().writeValueAsString(registrationDto);
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register")
                            .content(jsonRequest)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                            .value("Pesho"))
                    .andReturn();
        }
    }

    @Test
    @Order(12)
    public void testActivateUser() throws Exception {
        Users user = this.testH2RepositoryUsers.findByUsername("Pesho");
        String verification = user.getVerificationCode();
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register/verify/{verification}", verification))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful activate your profile"))
                .andReturn();
    }

    @Test
    @Order(13)
    public void testLoginUser() throws Exception {
        AuthCredentialRequest authCredentialRequest = new AuthCredentialRequest();
        authCredentialRequest.setUsername("Pesho");
        authCredentialRequest.setPassword("asdasd");
        String jsonRequest = new ObjectMapper().writeValueAsString(authCredentialRequest);
        mockMvc.perform(MockMvcRequestBuilders.post(authBaseURL + "/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username")
                        .value("Pesho"))
                .andReturn();
    }

    @Test
    @Order(14)
    public void testForgottenPasswordSendLink() throws Exception {
        ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto = new ForgottenPasswordNewPasswordDto();
        Users user = this.testH2RepositoryUsers.findByUsername("Pesho");
        forgottenPasswordNewPasswordDto.setVerificationCode(user.getVerificationCode());
        forgottenPasswordNewPasswordDto.setPassword("asdasd");
        forgottenPasswordNewPasswordDto.setConfirmPassword("asdasd");
        String jsonRequest = new ObjectMapper().writeValueAsString(forgottenPasswordNewPasswordDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/register/forgotten-password/new-password")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Order(15)
    @WithUserDetails("Tosho")
    public void testPromoteUser() throws Exception {
        Long userId = 4L;
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/admin/promote/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value("Successful Promote"))
                .andReturn();
    }

    @Test
    @Order(16)
    @WithUserDetails("Tosho")
    public void testDeleteUser() throws Exception {
        Long userId = 4L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/admin/{id}", userId))
                .andExpect(status().isOk());
        Assertions.assertEquals(3, testH2RepositoryUsers.count());
    }


    @Test
    @Order(17)
    public void testUnsuccessfulRegistration() throws Exception {
        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername("");
        registrationDto.setFirstName("");
        registrationDto.setLastName("");
        registrationDto.setEmail("");
        registrationDto.setPassword("");
        registrationDto.setAddress("");
        registrationDto.setPhoneNumber("");
        String jsonRequest = new ObjectMapper().writeValueAsString(registrationDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usernameError")
                        .value(USERNAME_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passwordError")
                        .value(PASSWORD_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailError")
                        .value(EMAIL_EMPTY_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstNameError")
                        .value(FIRST_NAME_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastNameError")
                        .value(LAST_NAME_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressError")
                        .value(ADDRESS_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumberError")
                        .value(PHONE_NUMBER_REGISTRATION_ERROR))
                .andReturn();
    }

    @Test
    @Order(18)
    public void testUnsuccessfulRegistration_PasswordsNotMatch() throws Exception {
        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername("Spiro");
        registrationDto.setFirstName("Spiro");
        registrationDto.setLastName("Spirov");
        registrationDto.setEmail("spiro@abv.bg");
        registrationDto.setPassword("asd");
        registrationDto.setConfirmPassword("123");
        registrationDto.setAddress("Sofiq");
        registrationDto.setPhoneNumber("0878998877");
        String jsonRequest = new ObjectMapper().writeValueAsString(registrationDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.confirmPasswordError")
                        .value(PASSWORD_NOT_MATCH_REGISTRATION_ERROR))
                .andReturn();
    }

    @Test
    @Order(19)
    public void testUnsuccessfulRegistration_UsernameAndEmailExist() throws Exception {
        UserRegistrationDTO registrationDto = new UserRegistrationDTO();
        registrationDto.setUsername("Victor");
        registrationDto.setFirstName("Spiro");
        registrationDto.setLastName("Spirov");
        registrationDto.setEmail("victor@abv.bg");
        registrationDto.setPassword("asd");
        registrationDto.setConfirmPassword("123");
        registrationDto.setAddress("Sofiq");
        registrationDto.setPhoneNumber("0878998877");
        String jsonRequest = new ObjectMapper().writeValueAsString(registrationDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.usernameError")
                        .value(USERNAME_EXIST_REGISTRATION_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailError")
                        .value(EMAIL_EXIST_REGISTRATION_ERROR))
                .andReturn();
    }

    @Test
    @Order(20)
    public void testSendInvalidEmailForForgottenPassword() throws Exception {
        ForgottenPasswordEmailDto forgottenPasswordEmailDto = new ForgottenPasswordEmailDto();
        forgottenPasswordEmailDto.setEmail("invalidEmail@abv.bg");
        String jsonRequest = new ObjectMapper().writeValueAsString(forgottenPasswordEmailDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "/register/forgotten-password")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value(INVALID_EMAIL))
                .andReturn();
    }

    @Test
    @Order(21)
    public void testUnsuccessfulChangePassword_PasswordsNotMatch() throws Exception {
        ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto = new ForgottenPasswordNewPasswordDto();
        Users user = this.testH2RepositoryUsers.findByUsername("Victor");
        forgottenPasswordNewPasswordDto.setVerificationCode(user.getVerificationCode());
        forgottenPasswordNewPasswordDto.setPassword("asd");
        forgottenPasswordNewPasswordDto.setConfirmPassword("123");
        String jsonRequest = new ObjectMapper().writeValueAsString(forgottenPasswordNewPasswordDto);
        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/register/forgotten-password/new-password")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.custom")
                        .value(INVALID_PASSWORD))
                .andReturn();
    }
}
