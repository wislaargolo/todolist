package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Username is required")
        @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
        String username,

        @NotBlank(message = "Email is required")
        String email
) { }
