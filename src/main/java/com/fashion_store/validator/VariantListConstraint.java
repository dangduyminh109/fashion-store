package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VariantListValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VariantListConstraint {
    String message() default "INVALID_ATTRIBUTE_COUNT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
