package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.AuthCredentialRequest;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://sladkarnicata-na-mama.azurewebsites.net"}, allowCredentials = "true", allowedHeaders = "true")
public class AuthController {

        private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UsersRepository usersRepository;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
    }

    @PostMapping("login")
    public ResponseEntity<?> login (@RequestBody AuthCredentialRequest request) {
        Optional<Users> validateUser = ifValidate(request);
        if (validateUser.isPresent()) {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    ));
            Users users = (Users) authenticate.getPrincipal();
            users.setPassword(null);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(users)
                    )
                    .body(users);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private Optional<Users> ifValidate(AuthCredentialRequest request) {
        return this.usersRepository.findByUsernameAndIsValidate(request.getUsername());
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token,
                                            @AuthenticationPrincipal Users user) {
        try {
            Boolean isTokenValid = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isTokenValid);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }
}
