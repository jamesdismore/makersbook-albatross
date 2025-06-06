package com.makersacademy.acebook.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirthNotNullAndOver14Constraint {
    String message() default "Users must be 14 or older";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
