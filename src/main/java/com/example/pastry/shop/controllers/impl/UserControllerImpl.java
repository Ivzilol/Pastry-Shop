package com.example.pastry.shop.controllers.impl;

import com.example.pastry.shop.controllers.UserController;
import com.example.pastry.shop.model.dto.*;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.response.CustomResponse;
import com.example.pastry.shop.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.pastry.shop.common.ConstantMessages.*;
import static com.example.pastry.shop.common.ExceptionMessages.*;

@RestController
@Tag(name = "Users")
public class UserControllerImpl implements UserController {

    private final UserServiceImpl userService;


    public UserControllerImpl(UserServiceImpl userService) {
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
    @Override
    public ResponseEntity<?> verificationUser(String verification) {
        Users user = this.userService.validateUser(verification);
        if (!user.getEmail().isEmpty()) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(SUCCESSFUL_ACTIVATE_PROFILE);
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
    @Override
    public ResponseEntity<?> createUser(UserRegistrationDTO userRegistrationDTO,
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
    @Override
    public ResponseEntity<?> getAllUsers(Users user) {
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
    @Override
    public ResponseEntity<?> deleteUser(Long id, Users user) {
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
    @Override
    public ResponseEntity<?> promoteUser(Long id, Users user) {
        boolean isPromote = this.userService.makeUserAdmin(id, user);
        if (isPromote) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(SUCCESSFUL_PROMOTE_USER);
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
    @Override
    public ResponseEntity<?> getCurrentUser(Users user) {
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
    @Override
    public ResponseEntity<?> getUserById(Long id) {
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
    @Override
    public ResponseEntity<?> updateUser(Long id, UpdateUserDTO updateUserDTO) {
        boolean updateUser = this.userService.updateUser(updateUserDTO, id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setCustom(updateUser ? SUCCESSFUL_UPDATE_USER : UNSUCCESSFUL_UPDATE_USER);
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
    @Override
    public ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto,
                                            Users user) {
        CustomResponse customResponse = new CustomResponse();
        boolean userChangePass = this.userService.changeUserPassword(changePasswordDto, user, customResponse);
        if (userChangePass) {
            customResponse.setCustom(SUCCESSFUL_CHANGE_PASSWORD);
        }
        return ResponseEntity.ok(customResponse);
    }

    @Operation(summary = "Forgotten password")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "202", description = "Send Email for new password"),
                    @ApiResponse(description = "Invalid Email",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class))})
            }
    )
    @Override
    public ResponseEntity<?> forgottenPasswordEmail(ForgottenPasswordEmailDto forgottenPasswordDto) throws MessagingException, UnsupportedEncodingException {
        Optional<Users> user = this.userService.findCurrentUserByEmail(forgottenPasswordDto.getEmail());
        if (user.isPresent()) {
            this.userService.sendEmailNewPassword(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(INVALID_EMAIL);
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
    @Override
    public ResponseEntity<?> forgottenPasswordNewPassword(ForgottenPasswordNewPasswordDto forgottenPasswordNewPasswordDto) {
        boolean newPassword = this.userService.forgottenPasswordSetNew(forgottenPasswordNewPasswordDto);
        if (newPassword) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setCustom(INVALID_PASSWORD);
            return ResponseEntity.ok(customResponse);
        }
    }
}
