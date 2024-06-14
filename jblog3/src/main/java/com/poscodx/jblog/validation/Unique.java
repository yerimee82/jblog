package com.poscodx.jblog.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UniqueNameValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Unique {
    String message() default "{Unique.userVo.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();

}
