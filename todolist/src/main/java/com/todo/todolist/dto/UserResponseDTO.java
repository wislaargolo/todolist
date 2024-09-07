package com.todo.todolist.dto;

public record UserResponseDTO(
        Long id,
        String name,
        String username,
        String email
) { }
