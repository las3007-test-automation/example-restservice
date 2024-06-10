package com.las3007.jsonTesting;

import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.restassured.filter.log.LogDetail;


public class Setup  implements BeforeAllCallback, AfterAllCallback{

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.BODY);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		RestAssured.reset();
	}
}
