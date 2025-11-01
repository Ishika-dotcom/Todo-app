package com.app.todo_app.repository;

import com.app.todo_app.model.Todo;
import com.app.todo_app.model.Todo.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for Todo.
 * Provides basic CRUD; we add finder by status.
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Find all by status with a Sort parameter
    List<Todo> findByStatus(Status status, Sort sort);
}

