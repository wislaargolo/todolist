package com.todo.todolist.dto.user;

import com.todo.todolist.util.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordDTO(

        @ValidPassword
        String currentPassword,
        @ValidPassword
        String newPassword
) { }
