package com.wellness.exceptions;

public class PaymentIdNotFoundException extends RuntimeException{

	private String message;
	public PaymentIdNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
