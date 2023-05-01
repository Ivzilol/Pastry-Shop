package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.UserService;
import com.example.pastry.shop.util.JwtUtil;
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

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    private ResponseEntity<?> createUse(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {

        userService.createUser(userRegistrationDTO);
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
                    .body(user);
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal Users user) {
        List<Users> allUsers = this.userService.findAllUser(user);
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.userService.deleteUser(id, user);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @PatchMapping("/admin/promote/{id}")
    public ResponseEntity<?> promoteUser(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        this.userService.makeUserAdmin(id, user);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
