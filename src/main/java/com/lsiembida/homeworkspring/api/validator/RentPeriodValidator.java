package com.lsiembida.homeworkspring.api.validator;

import com.lsiembida.homeworkspring.domain.book.Book;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RentPeriodValidator implements ConstraintValidator<RentPeriod, Book> {
    @Override
    public boolean isValid(Book book, ConstraintValidatorContext constraintValidatorContext) {
        return book.getRentedFrom().isBefore(book.getRentedTo());
    }
    @Override
    public void initialize(RentPeriod constraintAnnotation) {

    }
}
