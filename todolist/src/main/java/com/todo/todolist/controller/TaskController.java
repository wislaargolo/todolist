package com.todo.todolist.controller;

import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.dto.TaskDTO;
import com.todo.todolist.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        TaskDTO savedTask = taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(true, "Task created successfully", savedTask, null));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO<List<TaskDTO>>> getByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.findByUserId(userId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Tasks found", tasks, null));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseDTO<TaskDTO>> getById(@PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.getById(taskId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Task found successfully", taskDTO, null));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Task deleted successfully", null, null));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseDTO<TaskDTO>> update (
                    @PathVariable Long taskId, @RequestBody @Valid TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.update(taskDTO, taskId);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Task updated successfully", updatedTask, null));
    }
}
