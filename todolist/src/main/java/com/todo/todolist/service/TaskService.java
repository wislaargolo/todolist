package com.todo.todolist.service;


import com.todo.todolist.dto.TaskDTO;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.model.Priority;
import com.todo.todolist.model.Task;
import com.todo.todolist.model.User;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Task task = TaskMapper.toEntity(taskDTO, user);
        Task savedTask = taskRepository.save(task);

        return TaskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> findTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + taskId + " não existe");
        }
    }

    public List<TaskDTO> findAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO update(Long taskId, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDTO.title());
            task.setCompleted(taskDTO.completed());
            task.setPriority(Priority.valueOf(taskDTO.priority()));

            Task updatedTask = taskRepository.save(task);
            return TaskMapper.toDTO(updatedTask);
        } else {
            throw new IllegalArgumentException("Tarefa com ID " + taskId + " não existe");
        }
    }
}
