package com.example.pastry.shop.validation;

import com.example.pastry.shop.service.impl.UserServiceImpl;
import com.example.pastry.shop.validation.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserServiceImpl userService;

    public UniqueUsernameValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return this.userService.findUserByUsername(value).isEmpty();
    }
}
