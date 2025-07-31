package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DisplayTypeValidator.class})
public @interface DisplayTypeConstraint {
    String message() default "Invalid display type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
