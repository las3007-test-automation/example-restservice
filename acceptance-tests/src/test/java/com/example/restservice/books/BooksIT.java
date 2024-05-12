package com.example.restservice.books;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.example.restservice.books.models.Book;
import com.example.restservice.environments.CIEnvironmentExtension;
import com.example.restservice.environments.LocalEnvironmentExtension;

import io.restassured.http.ContentType;

@Tag("acceptance")
@ExtendWith(LocalEnvironmentExtension.class)
//@ExtendWith(DevEnvironmentExtension.class)
//@ExtendWith(CIEnvironmentExtension.class)
public class BooksIT {

    @Test
    public void testCreateBook() {
        Book newBook = new Book(null, "Test Book", "Test Author");

        given()
            .contentType(ContentType.JSON)
            .body(newBook)
        .when()
            .post("/books")
        .then()
            .statusCode(200)
            .body("title", equalTo(newBook.title()))
            .body("author", equalTo(newBook.author()));
    }

    @Test
    public void testGetAllBooks() {
        given()
        .when()
            .get("/books")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @ParameterizedTest
    @MethodSource("bookCreator")
    public void testGetBookById(Book book) {
        Long existingBookId = book.id();

        given()
        .when()
            .get("/books/{id}", existingBookId)
        .then()
            .statusCode(200)
            .body("id", equalTo(existingBookId.intValue()));
    }

    @ParameterizedTest
    @MethodSource("bookCreator")
    public void testUpdateBook(Book book) {
        Long existingBookId = book.id();
        Book updatedBook = new Book(null, "Updated Book", "Updated Author");

        given()
            .contentType(ContentType.JSON)
            .body(updatedBook)
        .when()
            .put("/books/{id}", existingBookId)
        .then()
            .statusCode(200)
            .body("title", equalTo(updatedBook.title()))
            .body("author", equalTo(updatedBook.author()));
    }

    @Test
    public void testDeleteBook() {
        // Assuming there is at least one book in the system
        Long existingBookId = 1L;

        given()
        .when()
            .delete("/books/{id}", existingBookId)
        .then()
            .statusCode(204);
    }
    
    @Test
    public void testCreateBookInvalidInput() {
        // Test creating a book with invalid input (missing required fields)
        given()
            .contentType("application/json")
            .body("{}")
        .when()
            .post("/books")
        .then()
            .statusCode(400);  // Bad Request
    }

    @Test
    public void testGetNonExistingBook() {
        // Test retrieving a non-existing book
        given()
        .when()
            .get("/books/9999")
        .then()
            .statusCode(404);  // Not Found
    }

    @Test
    public void testUpdateNonExistingBook() {
        // Test updating a non-existing book
        given()
            .contentType("application/json")
            .body("{\"title\": \"Updated Book\", \"author\": \"Updated Author\"}")
        .when()
            .put("/books/9999")
        .then()
            .statusCode(404);  // Not Found
    }

    @Test
    public void testDeleteNonExistingBook() {
        // Test deleting a non-existing book
        given()
        .when()
            .delete("/books/9999")
        .then()
            .statusCode(404);  // Not Found
    }

    @ParameterizedTest
    @MethodSource("bookCreator")
    public void testCreateBookDuplicateId(Book book) {
        // Test creating a book with a duplicate ID
        given()
            .contentType("application/json")
            .body("{\"id\": " + book.id() + ", \"title\": \"Duplicate Book\", \"author\": \"Duplicate Author\"}")
        .when()
            .post("/books")
        .then()
            .statusCode(409);  // Conflict
    }

    private static Stream<Book> bookCreator() {
        Book book = new Book(null, "Another Test Book", "bookCreator");

        Book createdBook = given()
            .contentType(ContentType.JSON)
            .body(book)
        .when()
            .post("/books")
        .then()
        	.extract()
        	.as(Book.class);
        
        return Stream.of(createdBook);
	}
}
