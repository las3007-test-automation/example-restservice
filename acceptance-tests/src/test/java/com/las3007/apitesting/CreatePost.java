package com.las3007.apitesting;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.restservice.environments.CIEnvironmentExtension;

@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class CreatePost {

	@Test
	public void createPost_FromFile_Success() throws IOException {
		File jsonFile = new File("src/test/resources/create-post-request.json");

		given()
			.body(jsonFile)
			.contentType("application/json")
		.when()
			.post("/posts")
		.then().assertThat()
			.statusCode(HTTP_CREATED).and()
			.body("$", not(empty())).and()
			.body("id", notNullValue())
			.body("title", equalTo("This is a Test Post"));
	}
}
