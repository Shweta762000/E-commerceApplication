package com.lcwd.electronic.store.electronicStore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
    private Logger logger= LoggerFactory.getLogger(GenderValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("gender feild validator");
        return value != null && (value.equalsIgnoreCase("Male") || value.equalsIgnoreCase("Female"));
    }
}
