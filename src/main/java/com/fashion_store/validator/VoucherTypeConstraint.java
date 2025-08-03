package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {VoucherTypeValidator.class})
public @interface VoucherTypeConstraint {
    String message() default "INVALID_VOUCHER_TYPE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
