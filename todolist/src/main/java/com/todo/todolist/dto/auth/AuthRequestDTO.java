package com.todo.todolist.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        String password
) { }
