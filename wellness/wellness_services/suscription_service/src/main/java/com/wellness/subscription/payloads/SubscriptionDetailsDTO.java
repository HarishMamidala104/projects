package com.wellness.subscription.payloads;

import lombok.Data;

@Data
public class SubscriptionDetailsDTO {
	
	private long id;

	private double price;

	private String planType;

}
