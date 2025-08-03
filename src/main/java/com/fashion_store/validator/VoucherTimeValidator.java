package com.fashion_store.validator;

import com.fashion_store.dto.request.VoucherRequest;
import com.fashion_store.dto.request.VoucherUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VoucherTimeValidator implements ConstraintValidator<VoucherTimeConstraint, Object> {
    @Override
    public boolean isValid(Object voucher, ConstraintValidatorContext constraintValidatorContext) {
        if (voucher == null)
            return true;
        if (voucher instanceof VoucherRequest request) {
            if (request.getEndDate() != null && request.getStartDate() != null
                    && request.getEndDate().isBefore(request.getStartDate())
            ) {
                return false;
            }
        }
        if (voucher instanceof VoucherUpdateRequest request) {
            if (request.getEndDate() != null && request.getStartDate() != null
                    && request.getEndDate().isBefore(request.getStartDate())
            ) {
                return false;
            }
        }
        return true;
    }
}
