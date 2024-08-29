package com.todo.todolist.controller;

import com.todo.todolist.dto.AuthRequestDTO;
import com.todo.todolist.dto.AuthResponseDTO;
import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<AuthResponseDTO>> authenticate(
            @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        true,
                        "Authentication completed successfully",
                        authenticationService.authenticate(request),
                        null));
    }
}
