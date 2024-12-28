package com.wellness.subscription.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.subscription.entities.SubscriptionDetails;
import com.wellness.subscription.exceptions.SubscriptionDetailsNotFound;
import com.wellness.subscription.exceptions.SubscriptionNotFound;
import com.wellness.subscription.payloads.SubscriptionDetailsDTO;
import com.wellness.subscription.repo.SubscriptionDetailsRepo;
import com.wellness.subscription.service.SubscriptionDetailsService;

@Service
public class SubscriptionDetailsServiceImpl implements SubscriptionDetailsService {

	@Autowired
	private SubscriptionDetailsRepo subscriptionDetailsRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String createSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
		String message = "failed to create subscription";
		SubscriptionDetails details = subscriptionDetailsRepo.save(subscriptionDetails);
		if (details != null) {
			message = "subscription created successful";
		}
		return message;
	}

	@Override
	public String updateSubscriptionDetails(SubscriptionDetails subscriptionDetails) {
		String message = "failed to update subscription";
		SubscriptionDetails details = subscriptionDetailsRepo.save(subscriptionDetails);
		if (details != null) {
			message = "subscription updated successful";
		}

		return message;
	}

	@Override
	public SubscriptionDetailsDTO viewSubcriptionDetails(long subscriptionId) {
		SubscriptionDetails subscriptionDetails = null;
		subscriptionDetails = subscriptionDetailsRepo.findById(subscriptionId)
				.orElseThrow(() -> new SubscriptionNotFound("subscription not found"));
		SubscriptionDetailsDTO map = modelMapper.map(subscriptionDetails, SubscriptionDetailsDTO.class);
		return map;
	}

	@Override
	public List<SubscriptionDetailsDTO> viewAllSubscriptionDetails() {
		List<SubscriptionDetails> allDetails = null;
		allDetails = subscriptionDetailsRepo.findAll();
		if (allDetails.isEmpty()) {
			throw new SubscriptionDetailsNotFound("subscription details not found");
		}

		List<SubscriptionDetailsDTO> collect = allDetails.stream()
				.map(details -> modelMapper.map(details, SubscriptionDetailsDTO.class)).collect(Collectors.toList());
		return collect;

	}

	@Override
	public String deleteSubscriptionDetails(long subscriptionId) {
		String message = "failed to delete";
		SubscriptionDetails subscriptionDetails = null;
		subscriptionDetails = subscriptionDetailsRepo.findById(subscriptionId)
				.orElseThrow(() -> new SubscriptionNotFound("subscription not found"));
		if (subscriptionDetails != null) {
			subscriptionDetailsRepo.deleteById(subscriptionId);
			message = "delete successfull";
		}
		return message;
	}

}
