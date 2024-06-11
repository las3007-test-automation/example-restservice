package com.example.restservice.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 5179967803342114036L;

	public ResourceAlreadyExistsException() {
		super();
	}

	public ResourceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResourceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

	public ResourceAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
