package com.todo.todolist.dto;

import com.todo.todolist.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskDTO (

        Long id,
        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Status is required")
        boolean completed,

        @NotNull(message = "Priority is required")
        String priority,

        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Due date is required")
        LocalDate dueDate

) {
}
