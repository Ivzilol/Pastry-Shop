package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000/", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "false", allowedHeaders = "true")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register/verify/{verification}")
    private ResponseEntity<?> verificationUser(@PathVariable String verification) {
        Users user = this.userService.validateUser(verification);
        if (!user.getEmail().isEmpty()) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Successful activate your profile");
            return ResponseEntity.ok(customResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    private ResponseEntity<?> createUse(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) throws MessagingException, UnsupportedEncodingException {
        userService.createUser(userRegistrationDTO);
        userService.sendVerificationEmail(userRegistrationDTO);
        UsersDTO  usersDTO = this.userService.findCurrentUser(userRegistrationDTO.getUsername());
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                                    userRegistrationDTO.getUsername(), userRegistrationDTO.getPassword()
                            )
                    );
            Users user = (Users) authentication.getPrincipal();
            user.setPassword(null);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(user)
                    )
                    .body(usersDTO);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @GetMapping("/admin")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal Users user) {
        List<UsersDTO> allUsers = this.userService.findAllUser(user);
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.userService.deleteUser(id, user);
        return  ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/promote/{id}")
    public ResponseEntity<?> promoteUser(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        boolean isPromote = this.userService.makeUserAdmin(id, user);
        if (isPromote) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Successful Promote");
            return ResponseEntity.ok(customResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Users user) {
        Optional<UsersDTO> currentUser = this.userService.getCurrentUser(user);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<UsersDTO> user = this.userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UpdateUserDTO updateUserDTO){
        boolean updateUser = this.userService.updateUser(updateUserDTO, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(updateUser ? "Successful update user!" : "Unsuccessful update user!");
        return ResponseEntity.ok(customResponse);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                            @AuthenticationPrincipal Users user) {
        boolean userChangePass = this.userService.changeUserPassword(changePasswordDto, user);
        if (userChangePass) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Successful change your password");
            return ResponseEntity.ok(customResponse);
        } else {
            return ResponseEntity.ok(null);
        }
    }
    @PostMapping("/register/forgotten-password")
    public ResponseEntity<?> forgottenPasswordEmail(@RequestBody ForgottenPasswordEmailDto forgottenPasswordDto) throws MessagingException, UnsupportedEncodingException {
        Optional<Users> user = this.userService.findCurrentUserByEmail(forgottenPasswordDto.getEmail());
        if (user.isPresent()) {
            this.userService.sendEmailNewPassword(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Invalid Email");
            return ResponseEntity.ok(customResponse);
        }
    }

    @PatchMapping("/register/forgotten-password/new-password")
    public ResponseEntity<?> forgottenPasswordNewPassword(@RequestBody ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto) {
        boolean newPassword = this.userService.forgottenPasswordSetNew(forgottenPasswordNewPasswordDto);
        if (newPassword) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom("Invalid Password");
            return ResponseEntity.ok(customResponse);
        }
    }
}
