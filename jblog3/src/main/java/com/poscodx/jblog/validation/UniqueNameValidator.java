package com.poscodx.jblog.validation;

import com.poscodx.jblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class UniqueNameValidator extends AbstractUniqueValidator{
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isNameValidate = userRepository.findByName(value) == null;

        if(!isNameValidate) {
            String errorMessage = "{Unique.userVo.name}";
            addConstraintViolation(context, errorMessage, value);
        }

        return isNameValidate;
    }
}
