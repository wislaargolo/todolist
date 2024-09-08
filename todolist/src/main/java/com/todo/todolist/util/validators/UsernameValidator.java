package com.todo.todolist.util.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !username.trim().isEmpty()
                && username.length() >= 5 && username.length() <= 20;
    }
}
