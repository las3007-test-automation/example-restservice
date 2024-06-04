package com.las3007.apitesting;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.restservice.environments.CIEnvironmentExtension;

@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class ModifyObject {

	@Test
	public void modifyPost_FromFiles_Success() throws IOException {
		File jsonFile = new File("src/test/resources/modify-post-request.json");

		given()
			.body(jsonFile)
			.contentType("application/json")
		.when()
			.put("/posts/{id}", 2)
		.then().assertThat()
			.statusCode(HTTP_OK).and()
			.body("$", not(empty()));

	}

	@Test
	public void modifyPost_FromFiles_Failure() throws IOException {
		File jsonFile = new File("src/test/resources/modify-post-request.json");

		given()
			.body(jsonFile)
			.contentType("application/json")
		.when()
			.put("/posts/{id}", 2342)
		.then().assertThat()
			.statusCode(HTTP_INTERNAL_ERROR);

	}
}
