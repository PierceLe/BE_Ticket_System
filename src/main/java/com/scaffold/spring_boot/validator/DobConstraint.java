package com.scaffold.spring_boot.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = { DobValidator.class }
)
public @interface DobConstraint {
    String message() default "Invalid dob";

    int min();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
