package com.todo.todolist.dto.user;

import com.todo.todolist.util.validators.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        Long id,

        @NotBlank
        String name,

        @ValidUsername
        String username,

        @Email
        @NotBlank
        String email
) { }
