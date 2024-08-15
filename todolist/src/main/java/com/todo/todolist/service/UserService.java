package com.todo.todolist.service;

import com.todo.todolist.dto.UserDTO;
import com.todo.todolist.dto.UserRegisterDTO;
import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO save(UserRegisterDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("O login " + user.getUsername() + " já está sendo usado.");
        }

        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO);
    }

    public UserDTO update(Long userId, UserUpdateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<User> existingUser = userRepository.findByUsername(userUpdateDTO.username());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                throw new IllegalArgumentException("O username " + userUpdateDTO.username() + " já está em uso por outro usuário.");
            }

            user.setUsername(userUpdateDTO.username());
            user.setName(userUpdateDTO.name());

            if (!PasswordEncoderUtil.encode(userUpdateDTO.password()).equals(user.getPassword())) {
                user.setPassword(PasswordEncoderUtil.encode(userUpdateDTO.password()));
            }

            User updatedUser = userRepository.save(user);
            return UserMapper.toDTO(updatedUser);
        } else {
            throw new IllegalArgumentException("Usuário com ID " + userId + " não existe");
        }
    }

    public void delete(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("Usuário com ID " + userId + " não existe");
        }
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

}
