package com.example.restservice.books;

import jakarta.validation.constraints.NotEmpty;

public record Book(Long id, @NotEmpty(message = "Book title cannot be null") String title, String author) {
}