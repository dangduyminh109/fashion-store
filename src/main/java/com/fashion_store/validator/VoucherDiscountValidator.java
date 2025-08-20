package com.fashion_store.validator;

import com.fashion_store.dto.voucher.request.VoucherRequest;
import com.fashion_store.dto.voucher.request.VoucherUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class VoucherDiscountValidator implements ConstraintValidator<VoucherDiscountConstraint, Object> {
    @Override
    public boolean isValid(Object voucher, ConstraintValidatorContext constraintValidatorContext) {
        if (voucher == null)
            return true;
        if (voucher instanceof VoucherRequest request) {
            if (request.getDiscountValue() == null || request.getDiscountValue().compareTo(BigDecimal.ZERO) < 0)
                return false;
            if (request.getMaxDiscountValue() != null && request.getMaxDiscountValue().compareTo(request.getDiscountValue()) < 0)
                return false;
        }
        if (voucher instanceof VoucherUpdateRequest request) {
            if (request.getDiscountValue() == null || request.getDiscountValue().compareTo(BigDecimal.ZERO) < 0)
                return false;
            if (request.getMaxDiscountValue() != null && request.getMaxDiscountValue().compareTo(request.getDiscountValue()) < 0)
                return false;
        }
        return true;
    }
}
