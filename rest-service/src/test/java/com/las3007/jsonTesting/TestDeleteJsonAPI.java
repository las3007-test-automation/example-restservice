package com.las3007.jsonTesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.when;

import static java.net.HttpURLConnection.HTTP_OK;

@ExtendWith(Setup.class)
public class TestDeleteJsonAPI {

	@Test
    public void deletePost () {

		when()
		.delete("/posts/{id}", 1)
			.then().assertThat()
			.statusCode(HTTP_OK);
    }
}
