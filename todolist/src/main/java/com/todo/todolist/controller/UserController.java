package com.todo.todolist.controller;

import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.dto.UserDTO;
import com.todo.todolist.dto.UserRegisterDTO;

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
    public ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            UserDTO responseDTO = userService.save(userRegisterDTO);

            return ResponseEntity.ok(new ResponseDTO<>(true, "Usuário registrado com sucesso", responseDTO, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(false, e.getMessage(), null, null));
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDTO<UserDTO>> update(
            @PathVariable Long userId,
            @RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        try {
            UserDTO updatedUserDTO = userService.update(userId, userRegisterDTO);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Usuário atualizado com sucesso", updatedUserDTO, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>(false, e.getMessage(), null, null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long userId) {
        try {
            userService.delete(userId);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Usuário deletado com sucesso", null, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null, null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(new ResponseDTO<>(true, "Usuários recuperados com sucesso", users, null));
    }

}
