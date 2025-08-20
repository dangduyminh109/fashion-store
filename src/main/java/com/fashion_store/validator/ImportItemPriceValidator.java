package com.fashion_store.validator;

import com.fashion_store.dto.importReceipt.request.ImportItemRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ImportItemPriceValidator implements ConstraintValidator<ImportItemPriceConstraint, ImportItemRequest> {

    @Override
    public boolean isValid(ImportItemRequest importItem, ConstraintValidatorContext context) {
        if (importItem == null) return true;
        if (importItem.getImportPrice() != null && importItem.getImportPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (importItem.getDiscountAmount() != null && importItem.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (importItem.getDiscountAmount() != null && importItem.getImportPrice() != null &&
                importItem.getDiscountAmount().compareTo(importItem.getImportPrice()) > 0)
            return false;
        return true;
    }
}
