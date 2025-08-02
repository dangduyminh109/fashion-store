package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceConstraint {
    String message() default "INVALID_PRICE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
