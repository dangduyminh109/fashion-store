package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
public @interface PhoneConstraint {
    String message() default "Invalid phone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
