package com.fashion_store.validator;

import com.fashion_store.enums.AuthProvider;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthProviderValidator implements ConstraintValidator<AuthProviderConstraint, String> {
    @Override
    public boolean isValid(String authProvider, ConstraintValidatorContext constraintValidatorContext) {
        if (authProvider == null) {
            return true;
        }
        try {
            AuthProvider.valueOf(authProvider.toUpperCase().trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
