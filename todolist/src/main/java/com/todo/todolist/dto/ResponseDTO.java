package com.todo.todolist.dto;

public record ResponseDTO<DTO>(
        boolean success,
        String message,
        DTO data,
        DTO error
) { }
