package com.fashion_store.validator;

import com.fashion_store.dto.request.VariantCreateRequest;
import com.fashion_store.dto.request.VariantUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class VariantListValidator implements ConstraintValidator<VariantListConstraint, List<?>> {
    @Override
    public boolean isValid(List<?> variantList, ConstraintValidatorContext constraintValidatorContext) {
        if (variantList == null)
            return true;
        if (variantList.size() == 1) {
            return true;
        } else {
            for (Object variant : variantList) {
                if (variant instanceof VariantUpdateRequest request) {
                    if (request.getAttributeValue() == null || request.getAttributeValue().isEmpty()) {
                        return false;
                    }
                } else if (variant instanceof VariantCreateRequest request) {
                    if (request.getAttributeValue() == null || request.getAttributeValue().isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
