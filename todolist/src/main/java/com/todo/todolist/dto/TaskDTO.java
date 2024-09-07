package com.todo.todolist.dto;

import java.time.LocalDate;

public record TaskDTO (
        Long id,
        String title,

        boolean completed,

        String priority,

        Long userId,

        LocalDate dueDate
) {

}
