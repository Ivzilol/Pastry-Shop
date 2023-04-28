package com.example.pastry.shop.validation.annotation;

import com.example.pastry.shop.validation.UniqueProductNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueProductNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {

    String message() default "Product already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
