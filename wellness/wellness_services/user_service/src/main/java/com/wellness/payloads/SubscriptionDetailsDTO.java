package com.wellness.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class SubscriptionDetailsDTO {
	@JsonIgnore
	private long planId;

	private double planPrice;

	private String planType;
}
