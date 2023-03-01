package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.UserDTO;
import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    private ResponseEntity<?> createUse(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.createUser(userRegistrationDTO);
    }


}
