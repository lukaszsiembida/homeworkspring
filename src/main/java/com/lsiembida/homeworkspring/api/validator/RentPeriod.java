package com.lsiembida.homeworkspring.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE) // adnotacja nad klasą
@Constraint(validatedBy = RentPeriodValidator.class)
public @interface RentPeriod {
    String message() default "Rent start date should be before end date"; // info o błędzie gdy walidacja nie przejdzie
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; //dane jakie dane będą walidowane
}
