package com.lsiembida.homeworkspring.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)  // w jakim miejscu ma się pojawić adnotacja
@Retention(value = RetentionPolicy.RUNTIME) // w jakim momencie adnotacja ma dać efekt( być sparsowana)
@Constraint(validatedBy = IsbnValidator.class)
public @interface Isbn {
    String message() default "ISBN should not be empty and should have 13 signs and starts with the '978'"; // info o błędzie gdy walidacja nie przejdzie
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; //dane jakie dane będą walidowane
}
