package com.todo.todolist.mapper;

import com.todo.todolist.dto.UserResponseDTO;
import com.todo.todolist.dto.UserRegisterDTO;
import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.model.User;

public class UserMapper {
    public static User toEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public static User toEntity(UserUpdateDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail());
    }
}