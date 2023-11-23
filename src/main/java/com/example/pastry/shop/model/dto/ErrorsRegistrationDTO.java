package com.example.pastry.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorsRegistrationDTO {

    private String usernameError;

    private String passwordError;

    private String emailError;

    private String firstNameError;

    private String lastNameError;

    private String addressError;

    private String phoneNumberError;

    private String confirmPasswordError;
}
