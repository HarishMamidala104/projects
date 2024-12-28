package com.wellness.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class SubscriberDetailsDTO {
	private long subscriptionId;

	@JsonIgnore
	private long userId;

	@JsonIgnore
	private long planId;

	private String company;

	private String designation;

	private String dateOfInception;

	private String department;

	private int noOfEmployees;

	private SubscriptionDetailsDTO planDetails;
}
