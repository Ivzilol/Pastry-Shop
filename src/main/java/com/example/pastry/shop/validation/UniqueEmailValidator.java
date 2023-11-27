package com.example.pastry.shop.validation;

import com.example.pastry.shop.service.impl.UserServiceImpl;
import com.example.pastry.shop.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


    private final UserServiceImpl userService;

    public UniqueEmailValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return this.userService.findUserByEmail(value) == null;
    }
}
