package com.todo.todolist.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.todolist.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        String errorMessage = "Erro de autenticação. Por favor, tente novamente.";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Credenciais inválidas. Verifique seu nome de usuário e senha.";
        } else {
            errorMessage = exception.getMessage();
        }

        ResponseDTO<String> responseDTO = new ResponseDTO<>(
                false,
                "Login falhou",
                null,
                errorMessage
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDTO));
    }
}