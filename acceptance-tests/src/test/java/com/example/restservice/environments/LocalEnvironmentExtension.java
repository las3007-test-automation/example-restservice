package com.example.restservice.environments;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.restassured.RestAssured;

public class LocalEnvironmentExtension implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		RestAssured.port = 8081;
		RestAssured.baseURI = "http://localhost";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		RestAssured.reset();
	}
}