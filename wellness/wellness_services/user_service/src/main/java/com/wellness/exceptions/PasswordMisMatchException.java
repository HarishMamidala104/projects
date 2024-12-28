package com.wellness.exceptions;

public class PasswordMisMatchException extends RuntimeException {

	private String message;

	public PasswordMisMatchException(String message) {
		super(message);
		this.message = message;
	}
}
