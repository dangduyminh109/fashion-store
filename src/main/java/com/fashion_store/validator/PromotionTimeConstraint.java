package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PromotionTimeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PromotionTimeConstraint {
    String message() default "INVALID_PROMOTION_TIME";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
