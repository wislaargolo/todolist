package com.todo.todolist.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        response.setHeader("User-Id", user.getUser().getId().toString());
        response.setHeader("Access-Control-Expose-Headers", "User-Id");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
