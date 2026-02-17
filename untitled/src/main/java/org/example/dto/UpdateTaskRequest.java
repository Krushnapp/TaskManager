package org.example.dto;

import org.example.model.TaskStatus;

import java.time.LocalDate;

public class UpdateTaskRequest {

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

