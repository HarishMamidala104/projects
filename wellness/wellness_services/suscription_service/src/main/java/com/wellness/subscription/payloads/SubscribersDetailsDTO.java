package com.wellness.subscription.payloads;

import com.wellness.subscription.entities.SubscriptionDetails;

import lombok.Data;

@Data
public class SubscribersDetailsDTO {
	
	private long subscriptionId;

	private UserDetailsDTO userDetails = new UserDetailsDTO();

	private SubscriptionDetails planDetails = new SubscriptionDetails();

	private String company;

	private String designation;

	private String dateOfInception;

	private String department;

	private int noOfEmployees;

	private int planDuration;
	
}
