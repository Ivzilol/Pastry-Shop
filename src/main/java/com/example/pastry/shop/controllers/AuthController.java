package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.AuthCredentialRequest;
import com.example.pastry.shop.model.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("login")
    ResponseEntity<?> login(@RequestBody AuthCredentialRequest request);

    @GetMapping("/validate")
    ResponseEntity<?> validateToken(@RequestParam String token,
                                    @AuthenticationPrincipal Users user);
}
