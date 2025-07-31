package com.fashion_store.validator;

import com.fashion_store.enums.AttributeDisplayType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DisplayTypeValidator implements ConstraintValidator<DisplayTypeConstraint, String> {
    @Override
    public boolean isValid(String displayType, ConstraintValidatorContext constraintValidatorContext) {
        if (displayType == null) {
            return true;
        }
        try {
            AttributeDisplayType.valueOf(displayType.toUpperCase().trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void initialize(DisplayTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
