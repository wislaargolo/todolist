package com.todo.todolist.service;

import com.todo.todolist.dto.UserResponseDTO;
import com.todo.todolist.dto.UserRegisterDTO;
import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.AttributeUtils;
import com.todo.todolist.util.PasswordEncoderUtil;
import com.todo.todolist.util.exception.BusinessException;
import com.todo.todolist.util.exception.ResourceNotFoundException;
import com.todo.todolist.util.validators.EntityFieldsValidator;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateBeforeSave(User user) {
        EntityFieldsValidator.validate(user);
        user.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
        validateUsername(user.getUsername(), user.getId());
        validateEmail(user.getEmail(), user.getId());
    }

    public void validateUsername(String username, Long id) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() &&  (id == null || !Objects.equals(user.get().getId(), id))){
            throw new BusinessException(
                    "Invalid username: " + username + ". There is already user with this username.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void validateEmail(String email, Long id) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() &&  (id == null || !Objects.equals(user.get().getId(), id))){
            throw new BusinessException(
                    "Invalid email: " + email + ". There is already user with this email.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public UserResponseDTO save(UserRegisterDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateBeforeSave(user);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserResponseDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO);
    }

    public UserResponseDTO update(UserUpdateDTO userUpdateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID " + userId + " not found"
                ));

        BeanUtils.copyProperties(userUpdateDTO, user, AttributeUtils.getNullOrBlankPropertyNames(userUpdateDTO));
        validateBeforeSave(user);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
    }

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

}
