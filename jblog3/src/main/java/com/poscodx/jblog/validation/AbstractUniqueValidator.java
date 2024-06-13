package com.poscodx.jblog.validation;

import com.poscodx.jblog.repository.UserRepository;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public abstract class AbstractUniqueValidator implements ConstraintValidator<Unique, String> {
    protected String field;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.field = constraintAnnotation.field();
    }

    @Override
    public abstract boolean isValid(String value, ConstraintValidatorContext context);

    protected void addConstraintViolation(ConstraintValidatorContext context, String messageTemplate, String propertyNode) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addPropertyNode(propertyNode)
                .addConstraintViolation();
    }
}
