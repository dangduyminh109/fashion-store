package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VoucherTimeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VoucherTimeConstraint {
    String message() default "INVALID_VOUCHER_TIME";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
