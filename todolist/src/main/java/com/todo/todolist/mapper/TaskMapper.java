package com.todo.todolist.mapper;

import com.todo.todolist.dto.TaskDTO;
import com.todo.todolist.model.Priority;
import com.todo.todolist.model.Task;
import com.todo.todolist.model.User;

public class TaskMapper {

    public static Task toEntity(TaskDTO dto, User user) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(dto.completed());
        task.setPriority(Priority.valueOf(dto.priority()));
        task.setUser(user);
        return task;
    }

    public static Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setCompleted(dto.completed());
        task.setPriority(Priority.valueOf(dto.priority()));
        return task;
    }

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(task.getTitle(), task.isCompleted(), task.getPriority().toString(), task.getUser().getId());
    }
}
