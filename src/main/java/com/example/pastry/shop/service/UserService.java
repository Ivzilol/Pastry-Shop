package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.util.CustomPasswordEncoder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    private final CustomPasswordEncoder customPasswordEncoder;

    private final AuthorityRepository authorityRepository;

    private final JavaMailSender javaMailSender;

    private final AuthenticationManager authenticationManager;

    private static final String VERIFICATION_MAIL_URL = "http://localhost:3000/register";

    private static final String FORGOTTEN_PASSWORD_URL = "http://localhost:3000/forgotten-password/";

    @Value("${admin_password}")
    private String adminPass;

    @Value("${auth_u}")
    private String auth_u;


    public UserService(UsersRepository usersRepository, CustomPasswordEncoder customPasswordEncoder, AuthorityRepository authorityRepository, JavaMailSender javaMailSender, AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authorityRepository = authorityRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
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
                && userRegistrationDTO.getPassword().equals(adminPass)) {
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
        String code = RandomString.make(64);
        newUser.setVerificationCode(code);
        usersRepository.save(newUser);
        return newUser;
    }

    public void sendVerificationEmail(UserRegistrationDTO userRegistrationDTO) throws MessagingException, UnsupportedEncodingException {
        Optional<Users> user = this.usersRepository.findByEmail(userRegistrationDTO.getEmail());
        String subject = "Successful Registration";
        String senderName = "Pastry Shop Team";
        String mailContent = "<h4>Dear " + userRegistrationDTO.getFirstName()
                + " " + userRegistrationDTO.getLastName() + ",</h4>";
        mailContent += "<p>Thank you for registration</p>";
        String verifyUrl = VERIFICATION_MAIL_URL + "/verify/" + user.get().getVerificationCode();
        mailContent += "<p>Please click on the \"ACTIVATE\" link to activate your account.<p/>";
        mailContent += "<h3><a href=\"" + verifyUrl + "\">ACTIVATE</a></h3>";
        mailContent += "<p>Mom's sweet shop team<p/>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ivailoali@gmail.com", senderName);
        helper.setTo(userRegistrationDTO.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public boolean updateUser(UpdateUserDTO updateUserDTO, Long id) {
        Users updateUser = this.usersRepository.findByUserId(id);
        Optional<Users> userUsername = this.usersRepository.findByUsername(updateUserDTO.getUsername());
        Optional<Users> userEmail = this.usersRepository.findByEmail(updateUserDTO.getEmail());
        // Checking whether the username and email with which he is trying to change them
        // are not taken by another user
        if (userUsername.isPresent() && !Objects.equals(userUsername.get().getId(), id)) {
            return false;
        } else {
            if (userEmail.isPresent() && !Objects.equals(id, userEmail.get().getId())) {
                return false;
            } else {
                updateUser.setPassword(updateUser.getPassword());
                updateUser.setUsername(updateUserDTO.getUsername());
                updateUser.setFirstName(updateUserDTO.getFirstName());
                updateUser.setLastName(updateUserDTO.getLastName());
                updateUser.setEmail(updateUserDTO.getEmail());
                updateUser.setAddress(updateUserDTO.getAddress());
                updateUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
                this.usersRepository.save(updateUser);
                return true;
            }
        }
    }

    public boolean makeUserAdmin(Long id, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            Authority authority = this.authorityRepository.findByUsersId(id);
            authority.setAuthority("admin");
            this.authorityRepository.save(authority);
            return true;
        } else {
            return false;
        }
    }

    public List<UsersDTO> findAllUser(Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            return this.usersRepository.findAllUsers(auth_u);
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

    public Optional<UsersDTO> getCurrentUser(Users user) {
        return this.usersRepository.findUserByUsername(user.getUsername());
    }

    public Optional<UsersDTO> getUserById(Long id) {
        return this.usersRepository.findUserById(id);
    }

    public Users validateUser(String verification) {
        Users user = this.usersRepository.findByVerificationCode(verification);
        if (!user.getEmail().isEmpty()) {
            user.setValidate(true);
            this.usersRepository.save(user);
        }
        return user;
    }

    public boolean changeUserPassword(ChangePasswordDto changePasswordDto, Users user) {
        boolean passwordMatch = ifPasswordMatch(changePasswordDto, user);
        if (passwordMatch) {
            String encodedPassword = customPasswordEncoder
                    .getPasswordEncoder().encode(changePasswordDto.getNewPassword());
            user.setPassword(encodedPassword);
            this.usersRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    private boolean ifPasswordMatch(ChangePasswordDto changePasswordDto, Users user) {
        boolean matchesOldPassword = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), changePasswordDto.getOldPassword()
                )).isAuthenticated();
        boolean matchesNewPassword = changePasswordDto.getNewPassword()
                .equals(changePasswordDto.getConfirmNewPassword());
        return matchesOldPassword && matchesNewPassword;
    }

    public void sendEmailNewPassword(Optional<Users> email) throws MessagingException, UnsupportedEncodingException {
        String subject = "Forgotten password";
        String senderName = "Pastry Shop Team";
        String mailContent = "<h4>Dear " + email.get().getFirstName()
                + " " + email.get().getLastName() + ",</h4>";
        mailContent += "<p>You have requested a generate new password.</p>";
        String verifyUrl = FORGOTTEN_PASSWORD_URL + email.get().getVerificationCode();
        mailContent += "<p>Please click on the \" NEW PASSWORD\" link to generate new password.<p/>";
        mailContent += "<h3><a href=\"" + verifyUrl + "\">NEW PASSWORD</a></h3>";
        mailContent += "<p>Mom's sweet shop team<p/>";
        UserTopClientService.sendMail(email, subject, senderName, mailContent, javaMailSender);
    }

    public boolean forgottenPasswordSetNew(ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto) {
        Users user = this.usersRepository
                .findByVerificationCode(forgottenPasswordNewPasswordDto.getVerificationCode());
        if (forgottenPasswordNewPasswordDto.getPassword() == null || user == null ||
                !forgottenPasswordNewPasswordDto.getPassword()
                        .equals(forgottenPasswordNewPasswordDto.getConfirmPassword())) {
            return false;
        } else {
            String encode = customPasswordEncoder.getPasswordEncoder()
                    .encode(forgottenPasswordNewPasswordDto.getPassword());
            user.setPassword(encode);
            this.usersRepository.save(user);
            return true;
        }
    }

    public UsersDTO findCurrentUser(String username) {
        return this.usersRepository.findCurrentUserByUsername(username);
    }

    public Optional<Users> findCurrentUserByEmail(String email) {
        return this.usersRepository.findByEmail(email);
    }

    public Optional<Users> validate(String username) {
        return this.usersRepository.findByUsernameAndIsValidate(username);
    }

    public void setErrors(List<String> errors, ErrorsRegistrationDTO errorsRegistrationDTO) {
        for (String error : errors) {
            switch (error) {
                case "Username length must be between 3 and 20 characters" ->
                        errorsRegistrationDTO.setUsernameError("Username length must be between 3 and 20 characters");
                case "Password length must be between 3 and 20 characters" ->
                        errorsRegistrationDTO.setPasswordError("Password length must be between 3 and 20 characters");
                case "Email cannot be empty" -> errorsRegistrationDTO.setEmailError("Email cannot be empty");
                case "First Name cannot be empty" ->
                        errorsRegistrationDTO.setFirstNameError("First Name cannot be empty");
                case "Last Name cannot be empty" -> errorsRegistrationDTO.setLastNameError("Last Name cannot be empty");
                case "Address cannot be empty" -> errorsRegistrationDTO.setAddressError("Address cannot be empty");
                case "Phone Number cannot be empty" ->
                        errorsRegistrationDTO.setPhoneNumberError("Phone Number cannot be empty");
                case "Email already exist" ->
                        errorsRegistrationDTO.setEmailError("Email already exist");
                case "Username already exist" ->
                        errorsRegistrationDTO.setUsernameError("Username already exist");
            }
        }
    }
}
