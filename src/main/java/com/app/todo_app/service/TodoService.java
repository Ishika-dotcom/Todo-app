package com.app.todo_app.service;

import com.app.todo_app.model.Todo;
import com.app.todo_app.model.Todo.Status;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to decouple controller from repository.
 */
public interface TodoService {
    List<Todo> findAll(String filter); // filter: "all", "pending", "done"
    Optional<Todo> findById(Long id);
    Todo save(Todo todo);
    void deleteById(Long id);
    Todo markAsDone(Long id);
}

