package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImportItemPriceValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportItemPriceConstraint {
    String message() default "INVALID_IMPORT_PRICE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
