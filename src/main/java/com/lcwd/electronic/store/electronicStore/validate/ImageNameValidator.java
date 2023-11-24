package com.lcwd.electronic.store.electronicStore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;


public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {
    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("check the msg for valid image: {}",value);

        if(value.isBlank()){
            return false;
        }
        else{
            return true;
        }

    }
}
