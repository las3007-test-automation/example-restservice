package com.example.restservice.environments;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;

public class CIEnvironmentExtension implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
//		RestAssured.port = 8081;
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.BODY);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		RestAssured.reset();
	}
}