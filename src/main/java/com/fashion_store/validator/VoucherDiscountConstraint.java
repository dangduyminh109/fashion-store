package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VoucherDiscountValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VoucherDiscountConstraint {
    String message() default "INVALID_VOUCHER_DISCOUNT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
