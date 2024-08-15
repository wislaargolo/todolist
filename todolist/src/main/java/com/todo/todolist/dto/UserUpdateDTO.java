package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Login é obrigatório")
        String username,

        String password
) { }
