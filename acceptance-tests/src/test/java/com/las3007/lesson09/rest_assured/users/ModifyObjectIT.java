package com.las3007.lesson09.rest_assured.users;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.las3007.lesson09.rest_assured.environments.CIEnvironmentExtension;

import io.restassured.http.ContentType;

@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class ModifyObjectIT {

	@Test
	public void modifyPost_FromFiles_Success() throws IOException {
		File jsonFile = new File("src/test/resources/modify-post-request.json");

		given()
			.contentType(ContentType.JSON)
			.body(jsonFile)
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
			.contentType(ContentType.JSON)
			.body(jsonFile)
		.when()
			.put("/posts/{id}", 2342)
		.then().assertThat()
			.statusCode(HTTP_INTERNAL_ERROR);

	}
}
