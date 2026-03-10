package org.example.service;


import org.example.dto.TaskEvent;
import org.example.dto.UpdateTaskRequest;
import org.example.exception.TaskNotFoundException;
import org.example.kafka.TaskProducer;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskProducer taskProducer;
    private final TaskRepository repository;

    public TaskService(TaskRepository taskRepository, TaskProducer taskProducer, TaskRepository repository) {
        this.taskRepository = taskRepository;
        this.taskProducer = taskProducer;
        this.repository = repository;
    }

    public Task createTask(Task task) {
        if (task.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }
        if(task.isDeleted()){
            throw new TaskNotFoundException(task.getId());
        }
        Task saved = repository.save(task);

        TaskEvent event = new TaskEvent();
        event.setTaskId(saved.getId());
        event.setTitle(saved.getTitle());
        event.setStatus(saved.getStatus().name());

        taskProducer.publish(event);

        return saved;
    }

    public Task getTask(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }


    public Task updateTask(String id, UpdateTaskRequest updatedTask) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (updatedTask.getDueDate() != null &&
                updatedTask.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }

        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());
        task.setDueDate(updatedTask.getDueDate());
        task.setDescription(updatedTask.getDescription());


        return repository.save(task);
    }


    public void deleteTask(String id) {
        Task task = getTask(id);
        task.setDeleted(true);
        repository.save(task);
    }

    public List<Task> getAllTasks(TaskStatus status, int page, int size) {
        List<Task> tasks = (status == null)
                ? repository.findAll()
                : repository.findByStatus(status);

        int from = page * size;
        int to = Math.min(from + size, tasks.size());

        if (from > tasks.size()) return List.of();
        return tasks.subList(from, to);
    }
}
