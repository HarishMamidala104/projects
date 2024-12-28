package com.wellness.subscription.service;

import java.util.List;

import com.wellness.subscription.entities.SubscriptionDetails;
import com.wellness.subscription.payloads.SubscriptionDetailsDTO;

public interface SubscriptionDetailsService {

	public String createSubscriptionDetails(SubscriptionDetails subscriptionDetails);

	public String updateSubscriptionDetails(SubscriptionDetails subscriptionDetails);

	public SubscriptionDetailsDTO viewSubcriptionDetails(long subscriptionId);

	public List<SubscriptionDetailsDTO> viewAllSubscriptionDetails();
	
	public String deleteSubscriptionDetails(long subscriptionId);
}
