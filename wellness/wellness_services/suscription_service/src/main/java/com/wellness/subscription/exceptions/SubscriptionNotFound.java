package com.wellness.subscription.exceptions;

public class SubscriptionNotFound extends RuntimeException {

	private String message;
	public SubscriptionNotFound(String message) {
		super(message);
		this.message = message;
	}
	
}
