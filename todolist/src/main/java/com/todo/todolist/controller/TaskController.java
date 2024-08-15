package com.todo.todolist.controller;

import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.dto.TaskDTO;
import com.todo.todolist.service.TaskService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<TaskDTO>> create(@RequestBody @Valid TaskDTO taskDTO) {
        try {
            TaskDTO savedTask = taskService.save(taskDTO);
            ResponseDTO<TaskDTO> response = new ResponseDTO<>(true, "Tarefa criada com sucesso", savedTask, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<TaskDTO> response = new ResponseDTO<>(false, "Falha na criação da Tarefa", null, null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO<List<TaskDTO>>> getTasksByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.findTasksByUserId(userId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Tarefas encontradas", tasks, null));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long taskId) {
        try {
            taskService.delete(taskId);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Tarefa deletada com sucesso", null, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null, null));
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDTO<TaskDTO>> update(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskDTO taskDTO) {
        try {
            TaskDTO updatedTask = taskService.update(taskId, taskDTO);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Tarefa atualizada com sucesso", updatedTask, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, e.getMessage(), null, null));
        }
    }

}