package com.todo.todolist.service;

import com.todo.todolist.dto.UserDTO;
import com.todo.todolist.dto.UserRegisterDTO;
import com.todo.todolist.dto.UserUpdateDTO;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.PasswordEncoderUtil;
import com.todo.todolist.util.exception.BusinessException;
import com.todo.todolist.util.exception.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateBeforeSave(User user) {
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

    public UserDTO save(UserRegisterDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateBeforeSave(user);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO);
    }

    public UserDTO update(UserUpdateDTO userUpdateDTO, Long userId) {
        User updatedUser= UserMapper.toEntity(userUpdateDTO);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID " + userId + " not found"
                ));

        BeanUtils.copyProperties(updatedUser, user, getNullPropertyNames(updatedUser));
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

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
