package com.example.pastry.shop.unitTesting;


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
    public void createUser() {
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
    }

    @Test
    public void UserRepositoryTest_SaveAll_ReturnUser() {
        Users user = new Users();
        user.setUsername("Ivoooooo");
        user.setFirstName("Ivaylo");
        user.setLastName("Alichkov");
        user.setAddress("Samokov");
        user.setEmail("ivo@abv.bg");
        user.setPhoneNumber("0887580832");
        user.setPassword("asdasd");
        user.setVerificationCode(RandomString.make(64));
        Users saveUser = usersRepository.save(user);
        Assertions.assertEquals(saveUser.getUsername(), "Ivoooooo");
    }

    @Test
    public void testFindByUsername(){
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
    public void testFindByVerificationCode(){
        Optional<Users> userByUsername = this.usersRepository.findByUsername("Pesho");
        Users userByVerificationCode = this.usersRepository
                .findByVerificationCode(userByUsername.get().getVerificationCode());
        Assertions.assertEquals(userByVerificationCode.getUsername(), "Pesho");
    }

    @Test
    public void testIsUserValidate() {

    }
}
