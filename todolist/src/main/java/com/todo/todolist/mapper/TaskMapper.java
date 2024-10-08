package com.todo.todolist.mapper;

import com.todo.todolist.dto.task.TaskDTO;
import com.todo.todolist.model.Priority;
import com.todo.todolist.model.Task;
import com.todo.todolist.model.User;

public class TaskMapper {

    public static Task toEntity(TaskDTO dto, User user) {
        Task task = new Task();
        task.setId(dto.id());
        task.setTitle(dto.title());
        task.setCompleted(dto.completed());
        task.setPriority(Priority.valueOf(dto.priority()));
        task.setUser(user);
        task.setDueDate(dto.dueDate());
        return task;
    }

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.isCompleted(), task.getPriority().toString(), task.getUser().getId(), task.getDueDate());
    }
}
