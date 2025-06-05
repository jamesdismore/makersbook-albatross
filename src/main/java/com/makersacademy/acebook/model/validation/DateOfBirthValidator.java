package com.makersacademy.acebook.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;



public class DateOfBirthValidator implements ConstraintValidator<DateOfBirthConstraint, LocalDate> {

    @Override
    public void initialize(DateOfBirthConstraint dob) { }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext cxt) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return dob.isBefore(tomorrow.minusYears(14));
    }
}


