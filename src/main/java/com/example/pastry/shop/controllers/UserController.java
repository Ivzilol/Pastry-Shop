package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.UpdateUserDTO;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080/", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "false", allowedHeaders = "true")
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
        return  ResponseEntity.ok().build();
    }

    @PatchMapping("/admin/promote/{id}")
    public ResponseEntity<?> promoteUser(@PathVariable Long id,
                                         @AuthenticationPrincipal Users user) {
        this.userService.makeUserAdmin(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Users user) {
        Optional<Users> currentUser = this.userService.getCurrentUser(user);
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<Users> user = this.userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UpdateUserDTO updateUserDTO,
                                        @AuthenticationPrincipal Users user){
        Users updateUser = this.userService.saveUser(updateUserDTO, id, user);
        return ResponseEntity.ok(updateUser);
    }
}
