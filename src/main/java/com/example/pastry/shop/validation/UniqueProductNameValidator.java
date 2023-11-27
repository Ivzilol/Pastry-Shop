package com.example.pastry.shop.validation;

import com.example.pastry.shop.service.impl.ProductsServiceImpl;
import com.example.pastry.shop.validation.annotation.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductsServiceImpl productsService;

    public UniqueProductNameValidator(ProductsServiceImpl productsService) {
        this.productsService = productsService;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return this.productsService.findByName(value).isEmpty();
    }
}
