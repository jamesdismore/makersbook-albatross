package com.makersacademy.acebook.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirthNotNullAndOver14Constraint, LocalDate> {

    @Override
    public void initialize(DateOfBirthNotNullAndOver14Constraint dob) { }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext cxt) {
        if (dob == null) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("Please set your date of birth").addConstraintViolation();
            return false;
        } else {
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            return dob.isBefore(tomorrow.minusYears(14));
        }
    }
}
