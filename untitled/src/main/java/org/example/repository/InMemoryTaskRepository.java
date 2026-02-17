package org.example.repository;

import org.example.model.Task;
import org.example.model.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<String, Task> store = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }



    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return store.values()
                .stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }
}
