package com.wellness.exceptions;

public class InvalidImageFileException extends RuntimeException {

	private String message;

	public InvalidImageFileException(String message) {
		super(message);
		this.message = message;
	}
	
}
