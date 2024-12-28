package com.wellness.subscription.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wellness.subscription.entities.SubscriberDetails;
import com.wellness.subscription.entities.SubscriptionDetails;
import com.wellness.subscription.exceptions.SubscriptionNotFound;
import com.wellness.subscription.payloads.SubscribersDetailsDTO;
import com.wellness.subscription.payloads.UserDetailsDTO;
import com.wellness.subscription.repo.SubscribersDetailsRepo;
import com.wellness.subscription.repo.SubscriptionDetailsRepo;
import com.wellness.subscription.service.SubscriberDetailsService;

@Service
public class SubscribersDetailsServiceImpl implements SubscriberDetailsService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SubscribersDetailsRepo subscribersDetailsRepo;

	@Autowired
	private SubscriptionDetailsRepo subscriptionDetailsRepo;

	@Override
	public String createSubscription(SubscriberDetails subscriberDetails) {
		String messgae = "failed to add subscriber";
		SubscriberDetails saveSubscriber = subscribersDetailsRepo.save(subscriberDetails);
		if (saveSubscriber != null) {
			messgae = "subscription successful";
		}
		return messgae;
	}

	@Override
	public List<SubscribersDetailsDTO> viewAllSubscriberDetails() {
		List<SubscriberDetails> subscribeDetails = subscribersDetailsRepo.findAll();
		List<SubscribersDetailsDTO> subscribersDto = null;
		subscribersDto = subscribeDetails.stream().map(subscriberDetails -> {
			SubscribersDetailsDTO dto = modelMapper.map(subscriberDetails, SubscribersDetailsDTO.class);
			SubscriptionDetails plan = subscriptionDetailsRepo.findById(subscriberDetails.getPlanId())
					.orElseThrow(() -> new SubscriptionNotFound("subscription not found"));
			dto.setPlanDetails(plan);

			UserDetailsDTO userDetails = restTemplate.getForObject(
					"http://USER-SERVICE/wellness/user/viewuser/" + subscriberDetails.getUserId(),
					UserDetailsDTO.class);
			
			dto.setUserDetails(userDetails);
			return dto;
		}).collect(Collectors.toList());

		return subscribersDto;
	}

}
