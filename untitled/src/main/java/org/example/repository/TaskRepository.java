package org.example.repository;

import org.example.model.Task;
import org.example.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String id);
    void deleteById(String id);
    List<Task> findAll();
    List<Task> findByStatus(TaskStatus status);
}
