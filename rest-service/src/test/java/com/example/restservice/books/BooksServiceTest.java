package com.example.restservice.books;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {
	
	private static Random RANDOM = new Random();

    @Spy
    private BooksService booksService;

    @Test
    public void testCreateBook_Success() throws Exception {
    	// Given
    	Book newBook = new Book(null, "Test Book", "Test Author");

    	// When
    	Book savedBook = booksService.createBook(newBook);

    	// Then
    	Assertions.assertThat(savedBook.title()).isNotNull();
    	Assertions.assertThat(savedBook.title()).isEqualTo(newBook.title());
    	Assertions.assertThat(savedBook.author()).isEqualTo(newBook.author());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testCreateBook_OptionalAuthor(String author) throws Exception {
    	// Given
    	Book newBook = new Book(null, "Test Book", author);

    	// When
    	Book savedBook = booksService.createBook(newBook);

    	// Then
    	Assertions.assertThat(savedBook.title()).isNotNull();
    	Assertions.assertThat(savedBook.title()).isEqualTo(newBook.title());
    	Assertions.assertThat(savedBook.author()).isEqualTo(newBook.author());
    }

    @Test
    public void testGetAllReturnsAllSavedBooks() throws Exception {
    	// Given
    	Book newBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book savedBook = booksService.createBook(newBook);

    	// When
    	List<Book> books = booksService.getAllBooks();

    	// Then
    	Assertions.assertThat(books).containsExactly(savedBook);
    }
    
    @Test
    public void testGetAllReturnsEmptyList() throws Exception {
    	// Given
    	// No data fixtures required

    	// When
    	List<Book> books = booksService.getAllBooks();

    	// Then
    	Assertions.assertThat(books).isEmpty();
    }

    @Test
    public void testGetBookById_ExistingBook() throws Exception {
    	// Given
    	Book newBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book savedBook = booksService.createBook(newBook);

    	// When
    	Optional<Book> returnedBook = booksService.getBookById(savedBook.id());

    	// Then
    	Assertions.assertThat(returnedBook).isNotEmpty();
    	Assertions.assertThat(returnedBook.get()).isEqualTo(savedBook);
    }

    @Test
    public void testGetBookById_DoesNotExist() throws Exception {
    	// Given
    	Book newBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book savedBook = booksService.createBook(newBook);

    	// When
    	Optional<Book> returnedBook = booksService.getBookById(savedBook.id() + 1);

    	// Then
    	Assertions.assertThat(returnedBook).isEmpty();
    }

    @NullSource
    @ParameterizedTest
    public void testGetBookById_NullBookId(Long bookId) throws Exception {
    	// Given
    	Book newBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	booksService.createBook(newBook);

    	// When
    	Optional<Book> returnedBook = booksService.getBookById(bookId);

    	// Then
    	Assertions.assertThat(returnedBook).isEmpty();
    }

    @Test
    public void testUpdateBook_ExistingBook() throws Exception {
    	// Given
    	Book newBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book existingBook = booksService.createBook(newBook);
    	Book updatedBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));

    	// When
    	Optional<Book> savedBook = booksService.updateBook(existingBook.id(), updatedBook);

    	// Then
    	Assertions.assertThat(savedBook).isNotEmpty();
    	Assertions.assertThat(savedBook.get().id()).isEqualTo(updatedBook.id());
    	Assertions.assertThat(savedBook.get().title()).isEqualTo(updatedBook.title());
    	Assertions.assertThat(savedBook.get().author()).isEqualTo(updatedBook.author());
    }

    @Test
    public void testUpdateBook_DoesNotExist() throws Exception {
    	// Given
    	Long randomBookId = RANDOM.nextLong();
    	Book updatedBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));

    	// When
    	Optional<Book> savedBook = booksService.updateBook(randomBookId, updatedBook);

    	// Then
    	Assertions.assertThat(savedBook).isEmpty();
    }

    @Test
    public void testDeleteBook_Success() throws Exception {
    	// Given
    	Book newBook1 = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book newBook2 = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Book savedBook1 = booksService.createBook(newBook1);
    	Book savedBook2 = booksService.createBook(newBook2);

    	// When
    	booksService.deleteBook(savedBook2.id());

    	// Then
    	List<Book> allBooks = booksService.getAllBooks();
    	Assertions.assertThat(allBooks).containsExactly(savedBook1);
    }
}
