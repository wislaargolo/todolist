package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        String name,
        String username,
        String email,
        String password
) { }
