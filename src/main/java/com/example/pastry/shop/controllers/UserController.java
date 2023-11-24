package com.example.pastry.shop.controllers;

import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.pastry.shop.common.ExceptionMessages.PASSWORD_NOT_MATCH_REGISTRATION_ERROR;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000/", "https://sladkarnicata-na-mama.azurewebsites.net/"}, allowCredentials = "false", allowedHeaders = "true")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Confirm Registration")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful activate your profile",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(responseCode = "400"),
            }
    )
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

    @Operation(summary = "Register")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successful registration",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDTO.class))}),
                    @ApiResponse(description = "Incorrect field",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorsRegistrationDTO.class))})
            }
    )
    @PostMapping("/register")
    private ResponseEntity<?> createUse(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                        BindingResult result) throws MessagingException, UnsupportedEncodingException {
        ResponseEntity<ErrorsRegistrationDTO> errorsRegistrationDTO =
                errorRegistration(userRegistrationDTO, result);
        if (errorsRegistrationDTO != null) return errorsRegistrationDTO;
        userService.createUser(userRegistrationDTO);
        userService.sendVerificationEmail(userRegistrationDTO);
        UsersDTO usersDTO = this.userService.findCurrentUser(userRegistrationDTO.getUsername());
        return ResponseEntity.ok(usersDTO);
    }

    @Nullable
    private ResponseEntity<ErrorsRegistrationDTO> errorRegistration(UserRegistrationDTO userRegistrationDTO, BindingResult result) {
        ErrorsRegistrationDTO errorsRegistrationDTO = new ErrorsRegistrationDTO();
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            this.userService.setErrors(errors, errorsRegistrationDTO);
            return ResponseEntity.ok().body(errorsRegistrationDTO);
        }
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            errorsRegistrationDTO.setConfirmPasswordError(PASSWORD_NOT_MATCH_REGISTRATION_ERROR);
            return ResponseEntity.ok().body(errorsRegistrationDTO);
        }
        return null;
    }

    @Operation(summary = "Get All Users", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Admin view all users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Not Authorise")}
    )
    @GetMapping("/admin")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal Users user) {
        List<UsersDTO> allUsers = this.userService.findAllUser(user);
        return ResponseEntity.ok(allUsers);
    }

    @Operation(summary = "Delete user", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User was delete"),
                    @ApiResponse(responseCode = "401", description = "Not Authorise")}
    )
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,
                                        @AuthenticationPrincipal Users user) {
        this.userService.deleteUser(id, user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Make user Admin", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Admin make user Admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Not Authorise"),
                    @ApiResponse(responseCode = "400", description = "Bad Request")}
    )
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

    @Operation(summary = "Get info for current user", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Not Authorise")}
    )
    @GetMapping("")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Users user) {
        Optional<UsersDTO> currentUser = this.userService.getCurrentUser(user);
        return ResponseEntity.ok(currentUser);
    }


    @Operation(summary = "Get user by ID", security = {
            @SecurityRequirement(name = "Authorization")
    })
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Get user by ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersDTO.class)))}
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UsersDTO> user = this.userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user profile")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User successful update profile",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(description = "Incorrect field",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))}),
            }
    )
    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UpdateUserDTO updateUserDTO) {
        boolean updateUser = this.userService.updateUser(updateUserDTO, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(updateUser ? "Successful update user!" : "Unsuccessful update user!");
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "User change password")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "User successful change password",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))}),
                    @ApiResponse(description = "Incorrect field")
            }
    )
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

    @Operation(summary = "Forgotten password")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "202", description = "Send Email for new password"),
                    @ApiResponse(description = "Invalid Email",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))})
            }
    )
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

    @Operation(summary = "Create new password")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "202", description = "Send Email for new password"),
                    @ApiResponse(description = "Invalid Password",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))})
            }
    )
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
