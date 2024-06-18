package com.las3007.lesson09.rest_assured.users;

import static io.restassured.RestAssured.when;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.greaterThan;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.las3007.lesson09.rest_assured.environments.CIEnvironmentExtension;


@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class GetPostIT {

	@Test
	public void post_Found() {
		when()
			.get("/posts")
		.then().assertThat()
			.statusCode(HTTP_OK).and()
			.body("$", not(empty())).and()
			.body("size()", greaterThan(0));
	}
	
	@Test
	public void specificPost_Found() {
		when()
			.get("/posts/{id}", 1)
		.then().assertThat()
			.statusCode(HTTP_OK).and()
			.body("$", not(empty())).and()
			.body("id", equalTo(1));
	}
	
	@Test
	public void post_NotFound() {
		when()
			.get("/posts/{id}", 1000)
		.then().assertThat()
			.statusCode(HTTP_NOT_FOUND).and()
			.body("$", Matchers.aMapWithSize(0));
	}
		
}