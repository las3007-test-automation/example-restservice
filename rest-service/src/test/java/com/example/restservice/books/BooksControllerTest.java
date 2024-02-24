package com.example.restservice.books;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.stream.IntStreams;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BooksController.class)
public class BooksControllerTest {
	
	private static Random RANDOM = new Random();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateBook_Success() throws Exception {
    	Book newBook = new Book(null, "Test Book", "Test Author");
    	Book savedBook = new Book(RANDOM.nextLong(), newBook.title(), newBook.author());

    	Mockito.when(booksService.createBook(newBook)).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedBook.id()))
                .andExpect(jsonPath("$.title").value(savedBook.title()))
                .andExpect(jsonPath("$.author").value(savedBook.author()));
    }

    @Test
    public void testCreateBook_InvalidJson() throws Exception {
    	Book newBook = new Book(null, "Test Book", "Test Author");
    	Book savedBook = new Book(RANDOM.nextLong(), newBook.title(), newBook.author());

    	Mockito.when(booksService.createBook(newBook)).thenReturn(savedBook);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"not a book\": \"some gibberish\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllBooks() throws Exception {
    	List<Book> books = IntStreams.range(RANDOM.nextInt(10))
    		.mapToObj(i -> new Book(RANDOM.nextLong(), randomAlphabetic(10), randomAlphabetic(10)))
    		.toList();

    	Mockito.when(booksService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*.title", hasItems(books.stream().map(Book::title).toArray())))
                .andExpect(jsonPath("$.*.author", hasItems(books.stream().map(Book::author).toArray())));
    }

    @Test
    public void testGetBookById_Found() throws Exception {
    	Book existingBook = new Book(RANDOM.nextLong(), randomAlphabetic(10), randomAlphabetic(10));

    	Mockito.when(booksService.getBookById(existingBook.id())).thenReturn(Optional.of(existingBook));

        mockMvc.perform(get("/books/{id}", existingBook.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingBook.id()));
    }

    @Test
    public void testGetBookById_NotFound() throws Exception {
    	Mockito.when(booksService.getBookById(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/{id}", RANDOM.nextLong()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook_Found() throws Exception {
    	Long existingBookId = RANDOM.nextLong();
    	Book updatedBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));
    	Optional<Book> bookResponse = Optional.of(new Book(existingBookId, updatedBook.title(), updatedBook.author()));

    	Mockito.when(booksService.updateBook(existingBookId, updatedBook)).thenReturn(bookResponse);

        mockMvc.perform(put("/books/{id}", existingBookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBook.title()))
                .andExpect(jsonPath("$.author").value(updatedBook.author()));
    }

    @Test
    public void testUpdateBook_NotFound() throws Exception {
    	Book updatedBook = new Book(null, randomAlphabetic(10), randomAlphabetic(10));

    	Mockito.when(booksService.updateBook(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/{id}", RANDOM.nextLong())
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook_MissingBody() throws Exception {
    	Mockito.when(booksService.updateBook(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/{id}", RANDOM.nextLong()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook() throws Exception {
    	Mockito.doNothing().when(booksService);

        mockMvc.perform(delete("/books/{id}", RANDOM.nextLong()))
                .andExpect(status().isNoContent());
    }
}
