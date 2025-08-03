package com.fashion_store.validator;

import com.fashion_store.enums.DiscountType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VoucherTypeValidator implements ConstraintValidator<VoucherTypeConstraint, String> {
    @Override
    public boolean isValid(String discountType, ConstraintValidatorContext constraintValidatorContext) {
        if (discountType == null) {
            return true;
        }
        try {
            DiscountType.valueOf(discountType.toUpperCase().trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
