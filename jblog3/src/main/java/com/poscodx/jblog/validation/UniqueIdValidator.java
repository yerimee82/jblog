package com.poscodx.jblog.validation;

import com.poscodx.jblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class UniqueIdValidator extends AbstractUniqueValidator{

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isIdValidate = userRepository.findById(value) == null;
        if(!isIdValidate) {
            String errorMessage = "{Unique.userVo.id}";
            addConstraintViolation(context, errorMessage);
        }

        return isIdValidate;
    }
}
