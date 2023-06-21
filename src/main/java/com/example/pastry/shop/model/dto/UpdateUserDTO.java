package com.example.pastry.shop.model.dto;

import com.example.pastry.shop.validation.annotation.UniqueEmail;
import com.example.pastry.shop.validation.annotation.UniqueUsername;

public class UpdateUserDTO {
    @UniqueUsername
    private String username;

    private String firstName;

    private String lastName;
    @UniqueEmail
    private String email;

    private String address;

    public UpdateUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
