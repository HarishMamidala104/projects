package com.wellness.exceptions;

public class ProfilePictureNotFoundException extends RuntimeException {

	private String message;

	public ProfilePictureNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
