package com.fashion_store.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {
    private static final String PHONE_REGEX = "^(\\+?[0-9]{1,4})?0?[1-9][0-9]{8}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.matches(PHONE_REGEX);
    }
}
