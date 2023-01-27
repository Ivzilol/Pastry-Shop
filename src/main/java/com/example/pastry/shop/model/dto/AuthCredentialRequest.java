package com.example.pastry.shop.model.dto;

public class AuthCredentialRequest {

    private String username;
    private String password;

    public AuthCredentialRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
