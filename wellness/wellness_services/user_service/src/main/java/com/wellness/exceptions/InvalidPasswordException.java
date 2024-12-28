package com.wellness.exceptions;

public class InvalidPasswordException extends RuntimeException {
	private String message;
	public InvalidPasswordException(String message) {
		super(message);
		this.message = message;
	}

}
