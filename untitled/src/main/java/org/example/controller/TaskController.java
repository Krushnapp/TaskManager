package org.example.controller;

import org.example.dto.TaskRequest;
import org.example.dto.UpdateTaskRequest;
import jakarta.validation.Valid;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody TaskRequest req) {
        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setStatus(req.getStatus());
        task.setDueDate(req.getDueDate());
        return service.createTask(task);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable String id) {
        return service.getTask(id);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id,
                       @RequestBody UpdateTaskRequest req) {
        return service.updateTask(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteTask(id);
    }

    @GetMapping
    public List<Task> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) TaskStatus status) {
        return service.getAllTasks(status, page, size);
    }
}
