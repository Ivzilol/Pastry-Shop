package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.AuthController;
import com.example.pastry.shop.model.dto.AuthCredentialRequest;
import com.example.pastry.shop.model.dto.UsersDTO;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.service.impl.UserServiceImpl;
import com.example.pastry.shop.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@Tag(name = "Authorization")
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;

    public AuthControllerImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Operation(summary = "User login")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User is Authorise",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersDTO.class))),
                    @ApiResponse(responseCode = "401", description = "User is not Authorise")}
    )
    @Override
    public ResponseEntity<?> login(AuthCredentialRequest request) {
        Optional<Users> validateUser = ifValidate(request);
        UsersDTO usersDTO = this.userService.findCurrentUser(request.getUsername());
        if (validateUser.isPresent()) {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    ));
            Users users = (Users) authenticate.getPrincipal();
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(users)
                    )
                    .body(usersDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private Optional<Users> ifValidate(AuthCredentialRequest request) {
        return this.userService.validate(request.getUsername());
    }

    @Operation(summary = "Validate user token", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User is Authorise"),
                    @ApiResponse(responseCode = "401", description = "Not Authorise")}
    )
    @Override
    public ResponseEntity<?> validateToken(String token, Users user) {
        try {
            Boolean isTokenValid = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isTokenValid);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }
}
