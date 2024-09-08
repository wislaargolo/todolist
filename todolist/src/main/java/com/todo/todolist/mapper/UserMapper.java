package com.todo.todolist.mapper;

import com.todo.todolist.dto.user.UserDTO;
import com.todo.todolist.dto.user.UserRegisterDTO;
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

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail());
    }
}