package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueEmail;
import com.example.pastry.shop.validation.annotation.UniqueUsername;
import com.unboundid.util.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @UniqueUsername
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters")
    @NotNull
    private String Username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters")
    @NotNull
    private String password;

    @UniqueEmail
    @Email(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String address;

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
}
