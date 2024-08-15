package com.todo.todolist.dto;

import com.todo.todolist.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskDTO (

        Long id,
        @NotBlank(message = "Título é obrigatório")
        String title,

        @NotNull(message = "Status é obrigatório")
        boolean completed,

        @NotNull(message = "Prioridade é obrigatórioy")
        String priority,

        @NotNull(message = "Usuário ID é obrigatório")
        Long userId

) {
}
