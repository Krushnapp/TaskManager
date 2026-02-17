package controller;

import org.example.controller.TaskController;
import org.example.model.Task;
import org.example.model.TaskStatus;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.example.exception.TaskNotFoundException;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Task");
        task.setStatus(TaskStatus.PENDING);
        task.setDueDate(LocalDate.now().plusDays(1));

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "title": "Task",
                      "description": "Desc",
                      "status": "PENDING",
                      "dueDate": "2030-01-01"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task"));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        Task task = new Task();
        task.setTitle("My Task");

        when(taskService.getTask("1")).thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("My Task"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        when(taskService.getTask("1"))
                .thenThrow(new TaskNotFoundException("1"));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldListTasksWithPagination() throws Exception {
        Task t1 = new Task();
        t1.setTitle("Task1");

        when(taskService.getAllTasks(null, 0, 5))
                .thenReturn(List.of(t1));

        mockMvc.perform(get("/tasks?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task1"));
    }
}
