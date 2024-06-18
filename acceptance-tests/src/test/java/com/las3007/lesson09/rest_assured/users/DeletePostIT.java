package com.las3007.lesson09.rest_assured.users;

import static io.restassured.RestAssured.when;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.las3007.lesson09.rest_assured.environments.CIEnvironmentExtension;

@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class DeletePostIT {


	@Test
	public void deletePost() {
		when()
			.get("/posts/{id}", 1)
		.then().assertThat()
			.statusCode(HTTP_OK);

	}
	
	@Test
	public void delete_NonExisting_Post() {
		when()
			.get("/posts/{id}", 1213243535)
		.then().assertThat()
			.statusCode(HTTP_NOT_FOUND);

	}
}
