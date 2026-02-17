package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.model.TaskStatus;

import java.time.LocalDate;

public class CreateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    private TaskStatus status;

    @NotNull
    private LocalDate dueDate;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
}
