package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Username is requires")
        String username,

        @NotBlank(message = "Password is required")
        String password
) { }
