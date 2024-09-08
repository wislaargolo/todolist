package com.todo.todolist.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskDTO (
        Long id,
        @NotBlank
        String title,
        @NotNull
        boolean completed,
        @NotBlank
        String priority,
        @NotNull
        Long userId,
        @NotNull
        LocalDate dueDate
) {

}
