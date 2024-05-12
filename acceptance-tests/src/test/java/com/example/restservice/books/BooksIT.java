package com.example.restservice.books;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.restservice.books.models.Book;
import com.example.restservice.environments.CIEnvironmentExtension;

import io.restassured.http.ContentType;

@Tag("acceptance")
//@ExtendWith(LocalEnvironmentExtension.class)
//@ExtendWith(DevEnvironmentExtension.class)
@ExtendWith(CIEnvironmentExtension.class)
public class BooksIT {

    @Test
    public void testCreateBook() {
        Book newBook = new Book(null, "Test Book", "Test Author");

        given()
            .contentType(ContentType.JSON)
            .body(newBook)
        .when()
            .post("/")
        .then()
            .statusCode(200)
            .body("title", equalTo(newBook.title()))
            .body("author", equalTo(newBook.author()));
    }

    @Test
    public void testGetAllBooks() {
        given()
        .when()
            .get("/")
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testGetBookById() {
        // Assuming there is at least one book in the system
        Long existingBookId = 1L;

        given()
        .when()
            .get("/" + existingBookId)
        .then()
            .statusCode(200)
            .body("id", equalTo(existingBookId.intValue()));
    }

    @Test
    public void testUpdateBook() {
        // Assuming there is at least one book in the system
        Long existingBookId = 1L;
        Book updatedBook = new Book(null, "Updated Book", "Updated Author");

        given()
            .contentType(ContentType.JSON)
            .body(updatedBook)
        .when()
            .put("/" + existingBookId)
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
            .delete("/" + existingBookId)
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
            .post("/")
        .then()
            .statusCode(400);  // Bad Request
    }

    @Test
    public void testGetNonExistingBook() {
        // Test retrieving a non-existing book
        given()
        .when()
            .get("/9999")
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
            .put("/9999")
        .then()
            .statusCode(404);  // Not Found
    }

    @Test
    public void testDeleteNonExistingBook() {
        // Test deleting a non-existing book
        given()
        .when()
            .delete("/9999")
        .then()
            .statusCode(404);  // Not Found
    }

    @Test
    public void testCreateBookDuplicateId() {
        // Test creating a book with a duplicate ID
        given()
            .contentType("application/json")
            .body("{\"id\": 1, \"title\": \"Duplicate Book\", \"author\": \"Duplicate Author\"}")
        .when()
            .post("/")
        .then()
            .statusCode(409);  // Conflict
    }
}
