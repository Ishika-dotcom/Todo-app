package com.app.todo_app.service;


import com.app.todo_app.model.Todo;
import com.app.todo_app.model.Todo.Status;
import com.app.todo_app.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of TodoService.
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;

    public TodoServiceImpl(TodoRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns list depending on filter.
     * Sort by createdAt desc (newest first).
     */
    @Override
    public List<Todo> findAll(String filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (filter == null || filter.equalsIgnoreCase("all")) {
            return repo.findAll(sort);
        } else if (filter.equalsIgnoreCase("pending")) {
            return repo.findByStatus(Status.PENDING, sort);
        } else if (filter.equalsIgnoreCase("done")) {
            return repo.findByStatus(Status.DONE, sort);
        } else {
            return repo.findAll(sort);
        }
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Todo save(Todo todo) {
        // If new, ensure createdAt set
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(java.time.LocalDateTime.now());
        }
        return repo.save(todo);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Todo markAsDone(Long id) {
        Optional<Todo> optional = repo.findById(id);
        if (optional.isPresent()) {
            Todo t = optional.get();
            t.setStatus(Status.DONE);
            return repo.save(t);
        } else {
            throw new IllegalArgumentException("Todo not found with id " + id);
        }
    }
}

