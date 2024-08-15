package com.todo.todolist.util.exception;

import java.time.ZonedDateTime;

public record ErrorDTO(ZonedDateTime timestamp, Integer status, String error, String message, String path) {
}