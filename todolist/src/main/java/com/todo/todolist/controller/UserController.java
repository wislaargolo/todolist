package com.todo.todolist.controller;

import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.dto.UserResponseDTO;
import com.todo.todolist.dto.UserRegisterDTO;

import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        UserResponseDTO responseDTO = userService.save(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(true, "User registered successfully", responseDTO, null));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> update(
                @PathVariable Long userId,
                @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        UserResponseDTO updatedUserDTO = userService.update(userUpdateDTO, userId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "User updated successfully", updatedUserDTO, null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "User deleted successfully", null, null));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(new ResponseDTO<>(true, "Users retrieved successfully", users, null));
    }
}
