package com.todo.todolist.mapper;

import com.todo.todolist.dto.UserDTO;
import com.todo.todolist.dto.UserRequestDTO;
import com.todo.todolist.model.User;
import com.todo.todolist.util.PasswordEncoderUtil;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setPassword(PasswordEncoderUtil.encode(dto.password()));
        return user;
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername());
    }
}