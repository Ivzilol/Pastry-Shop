package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.AuthCredentialRequest;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

//    @Value("localhost")
//    private String domain;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("login")
    public ResponseEntity<?> login (@RequestBody AuthCredentialRequest request) {
        try {
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
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    http://localhost:8080/api/auth/validate?token=something
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

//    @GetMapping("/logout")
//    public ResponseEntity<?> logout () {
//        ResponseCookie cookie = ResponseCookie.from("jwt", "")
//                .domain(domain)
//                .path("/")
//                .maxAge(0)
//                .build();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
//    }
}
