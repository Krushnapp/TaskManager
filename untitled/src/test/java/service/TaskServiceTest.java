package service;


import org.example.dto.UpdateTaskRequest;
import org.example.exception.TaskNotFoundException;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldCreateTaskWithFutureDate() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Desc");
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now().plusDays(2));

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(task);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void shouldThrowExceptionWhenDueDateIsPast() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDueDate(LocalDate.now().minusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(task));
    }

    @Test
    void shouldGetTaskById() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        Task result = taskService.getTask("1");

        assertNotNull(result);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(taskRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTask("99"));
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        taskService.deleteTask("1");

        verify(taskRepository).deleteById(task.getId());
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task();
        task.setTitle("Old");

        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        UpdateTaskRequest req = new UpdateTaskRequest();
        req.setTitle("New");

        Task updated = taskService.updateTask("1", req);

        assertEquals("New", updated.getTitle());
    }

    @Test
    void shouldReturnPaginatedTasks() {
        Task t1 = new Task();
        t1.setDueDate(LocalDate.now().plusDays(1));
        Task t2 = new Task();
        t2.setDueDate(LocalDate.now().plusDays(2));

        when(taskRepository.findAll()).thenReturn(List.of(t1, t2));

        List<Task> result = taskService.getAllTasks(null, 0, 1);

        assertEquals(1, result.size());
    }
}

