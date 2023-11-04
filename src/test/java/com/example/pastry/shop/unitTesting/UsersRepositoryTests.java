package com.example.pastry.shop.unitTesting;


import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

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
        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser.getId()).isGreaterThan(0);
    }
}
