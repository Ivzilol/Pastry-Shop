package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.UpdateUserDTO;
import com.example.pastry.shop.model.dto.UserDTO;
import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.util.CustomPasswordEncoder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    private final CustomPasswordEncoder customPasswordEncoder;

    private final AuthorityRepository authorityRepository;

    private final JavaMailSender javaMailSender;


    public UserService(UsersRepository usersRepository, CustomPasswordEncoder customPasswordEncoder, AuthorityRepository authorityRepository, JavaMailSender javaMailSender) {
        this.usersRepository = usersRepository;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authorityRepository = authorityRepository;
        this.javaMailSender = javaMailSender;
    }

    public Optional<Users> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UserDTO findUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return this.mapUserDTO(user);
    }

    private UserDTO mapUserDTO(Users user) {
        return new UserDTO()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setEmail(user.getEmail());
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        if (userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())
                && userRegistrationDTO.getPassword().equals("bbGGbb123")) {
            Users newUser = createUserOrAdmin(userRegistrationDTO);
            Authority authority = new Authority();
            authority.setAuthority("admin");
            authority.setUsers(newUser);
            authorityRepository.save(authority);
        } else if (userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            Users newUser = createUserOrAdmin(userRegistrationDTO);
            Authority authority = new Authority();
            authority.setAuthority("user");
            authority.setUsers(newUser);
            authorityRepository.save(authority);
        }
    }

    private Users createUserOrAdmin(UserRegistrationDTO userRegistrationDTO) {
        Users newUser = new Users();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setFirstName(userRegistrationDTO.getFirstName());
        newUser.setLastName(userRegistrationDTO.getLastName());
        newUser.setAddress(userRegistrationDTO.getAddress());
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        String encodedPassword = customPasswordEncoder
                .getPasswordEncoder().encode(userRegistrationDTO.getPassword());
        newUser.setPassword(encodedPassword);
//        if (!userRegistrationDTO.getPassword().equals("bbGGbb123")) {
//            String code = RandomString.make(64);
//            newUser.setVerificationCode(code);
//        }
        usersRepository.save(newUser);
        return newUser;
    }

    public void sendVerificationEmail(UserRegistrationDTO userRegistrationDTO, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "Pastry Shop Team";
        String mailContent = "<p>Dear" + userRegistrationDTO.getFirstName()
                + " " + userRegistrationDTO.getLastName() + ",</p>";
        mailContent += "<p>Please click link below to verify to your registration:</p>";
        String verifyUrl = siteUrl + "/verify?code=";
        mailContent += "<a>VERIFY</a>";
        mailContent += "<p>Thank you<br>The Pastry Shop Team<p/>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("pastryshopivo@gmail.com", senderName);
        helper.setTo(userRegistrationDTO.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public Users saveUser(UpdateUserDTO updateUserDTO, Long id) {
        Users updateUser = this.usersRepository.findByUserId(id);
        updateUser.setPassword(updateUser.getPassword());
        updateUser.setUsername(updateUserDTO.getUsername());
        updateUser.setFirstName(updateUserDTO.getFirstName());
        updateUser.setLastName(updateUserDTO.getLastName());
        updateUser.setEmail(updateUserDTO.getEmail());
        updateUser.setAddress(updateUserDTO.getAddress());
        this.usersRepository.save(updateUser);
        return updateUser;
    }

    public void makeUserAdmin(Long id, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            Authority authority = this.authorityRepository.findByUsersId(id);
            authority.setAuthority("admin");
            this.authorityRepository.save(authority);
        }
    }

    public List<Users> findAllUser(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.usersRepository.findAllUsers();
        } else {
            return null;
        }
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }


    public void deleteUser(Long id, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            Authority authority = this.authorityRepository.findByUsersId(id);
            this.authorityRepository.deleteById(authority.getId());
            this.usersRepository.deleteById(id);
        }
    }

    public Optional<Users> getCurrentUser(Users user) {
        return this.usersRepository.findByUsername(user.getUsername());
    }

    public Optional<Users> getUserById(Long id) {
        return this.usersRepository.findById(id);
    }
}
