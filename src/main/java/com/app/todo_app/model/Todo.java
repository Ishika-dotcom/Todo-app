package com.app.todo_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * JPA entity representing a Todo.
 */
@Entity
@Table(name = "todos")
public class Todo {

    // Primary key with auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title required, between 3 and 100 characters
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    // Optional description
    @Column(columnDefinition = "TEXT")
    private String description;

    // Enum stored as string: PENDING or DONE
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    // Timestamp when created
    private LocalDateTime createdAt;

    // Default constructor for JPA
    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Enum for status
    public enum Status {
        PENDING,
        DONE
    }
}