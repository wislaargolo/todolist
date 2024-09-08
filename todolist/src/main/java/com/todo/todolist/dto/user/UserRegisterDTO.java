package com.todo.todolist.dto.user;

import com.todo.todolist.util.validators.ValidPassword;
import com.todo.todolist.util.validators.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank
        String name,
        @ValidUsername
        String username,
        @Email
        @NotBlank
        String email,

        @ValidPassword
        String password
) { }
