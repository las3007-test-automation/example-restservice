package com.las3007.jsonTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matchers;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

@ExtendWith(Setup.class)
public class TestGetJsonRequest{

	@Test
    public void specificPost_Found () {

        given ().when ()
        .get("/posts/{id}", 1)
            .then ()
            .statusCode (HTTP_OK)
            .and ()
            .assertThat ()
            .body ("id", equalTo (1));
    }
	
	@Test
    public void postsFound () {

		given ().when()
		.get("/posts")
			.then().assertThat()
			.statusCode(HTTP_OK).and()
			.body("$", not(empty())).and()
			.body("size()", greaterThan(0));
    }
	
	@Test
	public void post_NotFound() {
		given ().when()
			.get("/posts/{id}", 1000)
		.then().assertThat()
			.statusCode(HTTP_NOT_FOUND).and()
			.body("$", Matchers.aMapWithSize(0));
	}
}
