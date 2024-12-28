package com.wellness.subscription.exceptions;

public class SubscriptionDetailsNotFound extends RuntimeException {

	private String message;

	public SubscriptionDetailsNotFound(String message) {
		super(message);
		this.message = message;
	}

}
