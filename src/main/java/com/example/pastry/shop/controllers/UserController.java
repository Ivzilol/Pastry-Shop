package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RequestMapping("/api/users")
public interface UserController {

    @PostMapping("/register/verify/{verification}")
    ResponseEntity<?> verificationUser(@PathVariable String verification);

    @PostMapping("/register")
    ResponseEntity<?> createUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                 BindingResult result) throws MessagingException, UnsupportedEncodingException;

    @GetMapping("/admin")
    ResponseEntity<?> getAllUsers(@AuthenticationPrincipal Users user);

    @DeleteMapping("/admin/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id,
                                 @AuthenticationPrincipal Users user);

    @PatchMapping("/admin/promote/{id}")
    ResponseEntity<?> promoteUser(@PathVariable Long id,
                                  @AuthenticationPrincipal Users user);

    @GetMapping("")
    ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Users user);

    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(@PathVariable Long id);

    @PatchMapping("/edit/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id,
                                 @RequestBody UpdateUserDTO updateUserDTO);

    @PatchMapping("/change-password")
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                     @AuthenticationPrincipal Users user);

    @PostMapping("/register/forgotten-password")
    ResponseEntity<?> forgottenPasswordEmail(@RequestBody ForgottenPasswordEmailDto forgottenPasswordDto) throws MessagingException, UnsupportedEncodingException;

    @PatchMapping("/register/forgotten-password/new-password")
    ResponseEntity<?> forgottenPasswordNewPassword(@RequestBody ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto);
}
