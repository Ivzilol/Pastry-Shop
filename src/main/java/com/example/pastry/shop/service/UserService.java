package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;


public interface UserService {

    Optional<Users> findUserByUsername(String username);

    UserDTO findUserByEmail(String email);

    void createUser(UserRegistrationDTO userRegistrationDTO);

    void sendVerificationEmail(UserRegistrationDTO userRegistrationDTO) throws MessagingException, UnsupportedEncodingException;

    boolean updateUser(UpdateUserDTO updateUserDTO, Long id);

    boolean makeUserAdmin(Long id, Users user);

    List<UsersDTO> findAllUser(Users user);

    void deleteUser(Long id, Users user);

    Optional<UsersDTO> getCurrentUser(Users user);

    Optional<UsersDTO> getUserById(Long id);

    Users validateUser(String verification);

    boolean changeUserPassword(ChangePasswordDto changePasswordDto, Users user, CustomResponse customResponse);

    void sendEmailNewPassword(Optional<Users> email) throws MessagingException, UnsupportedEncodingException;

    boolean forgottenPasswordSetNew(ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto);

    UsersDTO findCurrentUser(String username);

    Optional<Users> findCurrentUserByEmail(String email);

    void setErrors(List<String> errors, ErrorsRegistrationDTO errorsRegistrationDTO);
}
