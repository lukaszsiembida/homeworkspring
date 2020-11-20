package com.lsiembida.homeworkspring.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    @Override
    public void initialize(Isbn constraintAnnotation) {
// odczyt informacji przekazanych w adnotacjach
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && isbn.matches("[9][7][8][0-9]{10}");
    }


}
