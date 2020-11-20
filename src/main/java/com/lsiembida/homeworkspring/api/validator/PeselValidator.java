package com.lsiembida.homeworkspring.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeselValidator implements ConstraintValidator<Pesel, String> {

    @Override
    public void initialize(Pesel constraintAnnotation) {
// odczyt informacji przekazanych w adnotacjach
    }

    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext constraintValidatorContext) {
        return pesel != null && pesel.matches("[0-9]{11}");
    }


}
