package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "Login is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
