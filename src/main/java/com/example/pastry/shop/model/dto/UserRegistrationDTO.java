package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueEmail;
import com.example.pastry.shop.validation.annotation.UniqueUsername;
import com.unboundid.util.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.example.pastry.shop.common.ExceptionMessages.*;

public class UserRegistrationDTO {

    @UniqueUsername
    @Size(min = 3, max = 20, message = USERNAME_REGISTRATION_ERROR)
    @NotNull
    private String Username;

    @Size(min = 3, max = 20, message = PASSWORD_REGISTRATION_ERROR)
    @NotNull
    private String password;

    @Size(min = 3, max = 20, message = PASSWORD_REGISTRATION_ERROR)
    @NotNull
    private String confirmPassword;

    @UniqueEmail
    @Email(message = EMAIL_VALID_REGISTRATION_ERROR)
    @NotBlank(message = EMAIL_EMPTY_REGISTRATION_ERROR)
    private String email;

    @NotBlank(message = FIRST_NAME_REGISTRATION_ERROR)
    private String firstName;

    @NotBlank(message = LAST_NAME_REGISTRATION_ERROR)
    private String lastName;

    @NotBlank(message = ADDRESS_REGISTRATION_ERROR)
    private String address;

    @NotBlank(message = PHONE_NUMBER_REGISTRATION_ERROR)
    private String phoneNumber;


    public UserRegistrationDTO() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
