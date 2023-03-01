package com.example.pastry.shop.validation.annotation;

import com.example.pastry.shop.validation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


