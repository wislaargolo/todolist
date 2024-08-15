package com.todo.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "Login é obrigatório")
        String username,

        @NotBlank(message = "Senha é obrigatório")
        String password
) {
}
