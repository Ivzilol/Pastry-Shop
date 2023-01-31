package com.example.pastry.shop.controllers;

import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
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


//    @PostMapping("/register")
//    private ResponseEntity<?>createUser(@RequestBody UserDTO userDTO) {
//            userService.createUserUser(userDTO);
//    }

}
