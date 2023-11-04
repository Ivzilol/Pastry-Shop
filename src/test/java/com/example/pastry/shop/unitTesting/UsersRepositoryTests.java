package com.example.pastry.shop.unitTesting;


import com.example.pastry.shop.model.dto.UsersDTO;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersRepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @BeforeEach
    public void createUsers() {
        Users user = new Users();
        user.setUsername("Pesho");
        user.setFirstName("Petat");
        user.setLastName("Petrov");
        user.setAddress("Sofiq");
        user.setEmail("pesho@abv.bg");
        user.setPhoneNumber("0887580832");
        user.setPassword("asdasd");
        user.setVerificationCode(RandomString.make(64));
        this.usersRepository.save(user);
        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUsers(user);
        this.authorityRepository.save(authority);
        Users user2 = new Users();
        user2.setUsername("Gosho");
        user2.setFirstName("Georgi");
        user2.setLastName("Georgiev");
        user2.setAddress("Sofiq");
        user2.setEmail("gosho@abv.bg");
        user2.setPhoneNumber("0887665544");
        user2.setPassword("asdasd");
        user2.setVerificationCode(RandomString.make(64));
        this.usersRepository.save(user2);
        Authority authority2 = new Authority();
        authority2.setAuthority("user");
        authority2.setUsers(user2);
        this.authorityRepository.save(authority2);
    }


    @Test
    public void testFindByUsername() {
        Optional<Users> userByUsername = this.usersRepository.findByUsername("Pesho");
        Assertions.assertEquals(userByUsername.get().getUsername(), "Pesho");
    }

    @Test
    public void testFindByEmail() {
        Optional<Users> findByEmail = this.usersRepository.findByEmail("pesho@abv.bg");
        Assertions.assertEquals(findByEmail.get().getEmail(), "pesho@abv.bg");
    }

    @Test
    public void testFindByUserId() {
        Optional<Users> userByUsername = this.usersRepository.findByUsername("Pesho");
        Users userById = this.usersRepository.findByUserId(userByUsername.get().getId());
        Assertions.assertEquals(userById.getUsername(), "Pesho");
    }

    @Test
    public void testFindByVerificationCode() {
        Optional<Users> userByUsername = this.usersRepository.findByUsername("Pesho");
        Users userByVerificationCode = this.usersRepository
                .findByVerificationCode(userByUsername.get().getVerificationCode());
        Assertions.assertEquals(userByVerificationCode.getUsername(), "Pesho");
    }

    @Test
    public void testIsUserValidate() {
        Optional<Users> userByUsername = this.usersRepository.findByUsername("Pesho");
        userByUsername.get().setValidate(true);
        Assertions.assertTrue(userByUsername.get().isValidate());
    }

    @Test
    public void testFindUserByUsername() {
        Optional<UsersDTO> userDTO = this.usersRepository.findUserByUsername("Pesho");
        Assertions.assertEquals(userDTO.get().getUsername(), "Pesho");
        Assertions.assertEquals(userDTO.get().getEmail(), "pesho@abv.bg");
        Assertions.assertEquals(userDTO.get().getPhoneNumber(), "0887580832");
    }

    @Test
    public void testFindAllUsers() {
        List<UsersDTO> usersDTOS = this.usersRepository.findAllUsers();
        Assertions.assertEquals(usersDTOS.size(), 2);
    }

    @Test
    public void testFindUserById() {
        Optional<UsersDTO> user = this.usersRepository.findUserByUsername("Pesho");
        Optional<UsersDTO> usersDTO = this.usersRepository.findUserById(user.get().getId());
        Assertions.assertEquals(usersDTO.get().getUsername(), "Pesho");
    }

    @Test
    public void testFindCurrentUserByUsername() {
        Optional<UsersDTO> user = this.usersRepository.findUserByUsername("Pesho");
        UsersDTO usersDTO = this.usersRepository.findCurrentUserByUsername(user.get().getUsername());
        Assertions.assertEquals(usersDTO.getUsername(), "Pesho");
        Assertions.assertEquals(usersDTO.getEmail(), "pesho@abv.bg");
        Assertions.assertEquals(usersDTO.getPhoneNumber(), "0887580832");
    }

    @Test
    public void testUsernameIsBusy() {
        Assertions.assertThrows(Exception.class,
                () -> {
                    Users user = new Users();
                    user.setUsername("Pesho");
                    user.setFirstName("Petat");
                    user.setLastName("Petrov");
                    user.setAddress("Sofiq");
                    user.setEmail("pesho@abv.bg");
                    user.setPhoneNumber("0887580832");
                    user.setPassword("asdasd");
                    user.setVerificationCode(RandomString.make(64));
                    this.usersRepository.save(user);
                    Authority authority = new Authority();
                    authority.setAuthority("user");
                    authority.setUsers(user);
                    this.authorityRepository.save(authority);
                });
    }

    @Test
    public void testEmailIsBusy() {
        Assertions.assertThrows(Exception.class,
                () -> {
                    Users user = new Users();
                    user.setUsername("Ivo");
                    user.setFirstName("Petat");
                    user.setLastName("Petrov");
                    user.setAddress("Sofiq");
                    user.setEmail("pesho@abv.bg");
                    user.setPhoneNumber("0887580832");
                    user.setPassword("asdasd");
                    user.setVerificationCode(RandomString.make(64));
                    this.usersRepository.save(user);
                    Authority authority = new Authority();
                    authority.setAuthority("user");
                    authority.setUsers(user);
                    this.authorityRepository.save(authority);
                });
    }

    @Test
    public void testUsernameIsNull() {
        Assertions.assertThrows(Exception.class,
                () -> {
                    Users user = new Users();
                    user.setUsername(null);
                    user.setFirstName("Petat");
                    user.setLastName("Petrov");
                    user.setAddress("Sofiq");
                    user.setEmail("pesho@abv.bg");
                    user.setPhoneNumber("0887580832");
                    user.setPassword("asdasd");
                    user.setVerificationCode(RandomString.make(64));
                    this.usersRepository.save(user);
                    Authority authority = new Authority();
                    authority.setAuthority("user");
                    authority.setUsers(user);
                    this.authorityRepository.save(authority);
                });
    }

}
