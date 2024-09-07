package com.todo.todolist.mapper;

import com.todo.todolist.dto.UserDTO;
import com.todo.todolist.dto.UserRegisterDTO;
import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.model.User;
import com.todo.todolist.util.PasswordEncoderUtil;

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

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail());
    }
}