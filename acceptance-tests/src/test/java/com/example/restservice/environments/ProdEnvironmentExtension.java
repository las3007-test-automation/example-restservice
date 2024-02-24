package com.example.restservice.environments;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.restassured.RestAssured;

public class ProdEnvironmentExtension implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		RestAssured.port = 443;
		RestAssured.baseURI = "https://rest-service.acme.net";
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		RestAssured.reset();
	}
}