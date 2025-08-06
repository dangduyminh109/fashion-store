package com.fashion_store.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PaymentMethodValidator.class})
public @interface PaymentMethodConstraint {
    String message() default "INVALID_PAYMENT_METHOD";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
