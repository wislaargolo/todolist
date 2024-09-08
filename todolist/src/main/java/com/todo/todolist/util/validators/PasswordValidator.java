package com.todo.todolist.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && !password.trim().isEmpty()
                && password.length() >= 5 && password.length() <= 15;
    }
}
