package com.example.restservice.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.restservice.exceptions.ResourceAlreadyExistsException;

@Service
public class BooksService {

	private final List<Book> books = new ArrayList<>();
	private long nextId = 1;

	// Create a new book
	public Book createBook(final Book book) {
		if (books.stream().anyMatch((b) -> b.id().equals(book.id()))) {
			throw new ResourceAlreadyExistsException();
		}
		Book newBook = new Book(nextId++, book.title(), book.author());
		books.add(newBook);
		return newBook;
	}

	// Get all books
	public List<Book> getAllBooks() {
		return books;
	}

	// Get a specific book by ID
	public Optional<Book> getBookById(Long id) {
		return books.stream().filter(book -> book.id().equals(id)).findFirst();
	}

	// Update a book by ID
	public Optional<Book> updateBook(Long id, Book updatedBook) {
		for (int i = 0; i < books.size(); i++) {
			Book existingBook = books.get(i);
			if (existingBook.id().equals(id)) {
				// Replace the existing book with a new instance
				books.set(i, new Book(id, updatedBook.title(), updatedBook.author()));
				return Optional.of(updatedBook);
			}
		}
		return Optional.empty();
	}

	// Delete a book by ID
	public boolean deleteBook(Long id) {
		return books.removeIf(book -> book.id().equals(id));
	}
}
