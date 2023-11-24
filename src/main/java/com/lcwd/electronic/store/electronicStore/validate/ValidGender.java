package com.lcwd.electronic.store.electronicStore.validate;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidGender {
    String message() default "Invalid gender. Must be Male or Female";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
