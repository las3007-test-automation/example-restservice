package com.las3007.apitesting;

import static io.restassured.RestAssured.when;
import static java.net.HttpURLConnection.HTTP_OK;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.las3007.restservice.environments.CIEnvironmentExtension;

@Tag("acceptance")
@ExtendWith(CIEnvironmentExtension.class)
public class DeletePost {

	@Test
	public void deletePost() {
		when()
			.delete("/posts/{id}", 1)
		.then().assertThat()
			.statusCode(HTTP_OK);

	}
	
}
