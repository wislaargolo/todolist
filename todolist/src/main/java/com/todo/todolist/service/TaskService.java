package com.todo.todolist.service;


import com.todo.todolist.dto.TaskDTO;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.model.Priority;
import com.todo.todolist.model.Task;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.util.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public TaskDTO save(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + taskDTO.userId() + " not found"));

        Task task = TaskMapper.toEntity(taskDTO, user);
        Task savedTask = taskRepository.save(task);

        return TaskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> findByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isPresent()) {
            return TaskMapper.toDTO(task.get());
        } else {
            throw new ResourceNotFoundException("Task with ID " + taskId + " not found");
        }
    }

    public void delete(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new ResourceNotFoundException("Task with ID " + taskId + " not found");
        }
    }

    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO update(TaskDTO taskDTO, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(
                "Task with ID " + taskId + " not found"
        ));

        task = TaskMapper.toEntity(taskDTO, task.getUser());
        task.setId(taskId);

        return TaskMapper.toDTO(taskRepository.save(task));

    }
}
