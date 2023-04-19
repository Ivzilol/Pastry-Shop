package com.example.pastry.shop.validation;

import com.example.pastry.shop.service.ProductsService;
import com.example.pastry.shop.validation.annotation.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductsService productsService;

    public UniqueProductNameValidator(ProductsService productsService) {
        this.productsService = productsService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return this.productsService.findByName(value) == null;
    }
}
