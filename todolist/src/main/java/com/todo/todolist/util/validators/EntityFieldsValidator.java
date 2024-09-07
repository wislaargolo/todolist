package com.todo.todolist.util.validators;

import com.todo.todolist.util.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;

import java.util.Set;

public class EntityFieldsValidator {

    public static <T> void validate(T object) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation Error: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(" ").append(violation.getMessage());
            }
            throw new BusinessException(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
