package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.restservice.exceptions.ResourceAlreadyExistsException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	protected ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex,
			WebRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Resource already exists");
	}
}