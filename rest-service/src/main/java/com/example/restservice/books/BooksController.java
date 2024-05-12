package com.example.restservice.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BooksService booksService;

	// Create a new book
	@PostMapping
	public Book createBook(@Valid @RequestBody Book book) {
		return booksService.createBook(book);
	}

	// Get all books
	@GetMapping
	public List<Book> getAllBooks() {
		return booksService.getAllBooks();
	}

	// Get a specific book by ID
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		return booksService.getBookById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Update a book by ID
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
		return booksService.updateBook(id, updatedBook)
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	// Delete a book by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		return booksService.deleteBook(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
