package com.fashion_store.validator;

import com.fashion_store.enums.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentMethodValidator implements ConstraintValidator<PaymentMethodConstraint, String> {
    @Override
    public boolean isValid(String paymentMethod, ConstraintValidatorContext constraintValidatorContext) {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            return true;
        }
        try {
            PaymentMethod.valueOf(paymentMethod.toUpperCase().trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
