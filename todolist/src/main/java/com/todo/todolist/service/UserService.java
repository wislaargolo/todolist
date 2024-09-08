package com.todo.todolist.service;

import com.todo.todolist.dto.user.UserDTO;
import com.todo.todolist.dto.user.UserRegisterDTO;
import com.todo.todolist.dto.user.UserUpdatePasswordDTO;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.AttributeUtils;
import com.todo.todolist.util.exception.BusinessException;
import com.todo.todolist.util.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateBeforeSave(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public UserDTO save(UserRegisterDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateBeforeSave(user);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO);
    }

    public UserDTO update(UserDTO userUpdateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID " + userId + " not found"
                ));

        BeanUtils.copyProperties(userUpdateDTO, user, AttributeUtils.getNullOrBlankPropertyNames(userUpdateDTO));
        validateBeforeSave(user);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public void validateBeforeChangePassword(
                String currentPassword,
                String newPassword, String currentPasswordDB
    ) {
        if (!passwordEncoder.matches(currentPassword, currentPasswordDB)) {
            throw new BusinessException("Current password is incorrect", HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(newPassword, currentPasswordDB)) {
            throw new BusinessException("New password cannot be the same as the current password", HttpStatus.BAD_REQUEST);
        }

    }

    public void changePassword(Long userId, UserUpdatePasswordDTO userUpdatePasswordDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String currentPasswordDB = user.getPassword();

        validateBeforeChangePassword(userUpdatePasswordDTO.currentPassword(), userUpdatePasswordDTO.newPassword(), currentPasswordDB);

        user.setPassword(passwordEncoder.encode(userUpdatePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    public void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User with ID " + userId + " not found");
        }
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

}
