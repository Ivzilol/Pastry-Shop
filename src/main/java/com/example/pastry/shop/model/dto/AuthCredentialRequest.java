package com.example.pastry.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class AuthCredentialRequest {

    private String username;
    private String password;
    @JsonCreator
    public AuthCredentialRequest() {
    }
    @JsonCreator
    public String getUsername() {
        return username;
    }
    @JsonCreator
    public void setUsername(String username) {
        this.username = username;
    }
    @JsonCreator
    public String getPassword() {
        return password;
    }
    @JsonCreator
    public void setPassword(String password) {
        this.password = password;
    }
}
