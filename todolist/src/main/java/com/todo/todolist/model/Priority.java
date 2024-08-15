package com.todo.todolist.model;

public enum Priority {

    BAIXA("Baixa prioridade"),
    MEDIA("Média prioridade"),
    ALTA("Alta prioridade"),

    URGENTE("Urgente");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
