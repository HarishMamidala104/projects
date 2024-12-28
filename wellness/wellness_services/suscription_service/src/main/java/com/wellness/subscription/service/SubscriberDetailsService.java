package com.wellness.subscription.service;

import java.util.List;

import com.wellness.subscription.entities.SubscriberDetails;
import com.wellness.subscription.payloads.SubscribersDetailsDTO;

public interface SubscriberDetailsService {
	public String createSubscription(SubscriberDetails subscriberDetails);
	
	public List<SubscribersDetailsDTO> viewAllSubscriberDetails();
}
