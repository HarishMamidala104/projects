package com.wellness.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.subscription.entities.SubscriptionDetails;
import com.wellness.subscription.payloads.SubscriptionDetailsDTO;
import com.wellness.subscription.service.SubscriptionDetailsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/wellness/subscription")
@CrossOrigin(origins = "*")
public class SubscriptionDetailsController {

	@Autowired
	private SubscriptionDetailsService subscriptionDetailsService;

	@PostMapping("/create")
	public ResponseEntity<String> createSubscriptionDetails(@RequestBody SubscriptionDetails details) {
		String createSubscriptionDetails = subscriptionDetailsService.createSubscriptionDetails(details);
		return new ResponseEntity<String>(createSubscriptionDetails, HttpStatus.CREATED);

	}

	@PatchMapping("/update")
	public ResponseEntity<String> updateSubscriptionDetails(@RequestBody SubscriptionDetails subscriptionDetails) {
		String updateSubscriptionDetails = subscriptionDetailsService.updateSubscriptionDetails(subscriptionDetails);
		return new ResponseEntity<String>(updateSubscriptionDetails, HttpStatus.OK);
	}

	@GetMapping("/getsubscription/{subscriptionId}")
	public ResponseEntity<SubscriptionDetailsDTO> getSubscriptionDetails(@PathVariable long subscriptionId) {
		SubscriptionDetailsDTO viewSubcriptionDetails = subscriptionDetailsService
				.viewSubcriptionDetails(subscriptionId);
		return new ResponseEntity<SubscriptionDetailsDTO>(viewSubcriptionDetails, HttpStatus.OK);
	}

	@GetMapping("/viewallsubscriptions")
	public ResponseEntity<List<SubscriptionDetailsDTO>> getAllSubscriptions() {
		List<SubscriptionDetailsDTO> viewAllSubscriptionDetails = subscriptionDetailsService
				.viewAllSubscriptionDetails();
		return new ResponseEntity<List<SubscriptionDetailsDTO>>(viewAllSubscriptionDetails, HttpStatus.OK);
	}

	
	@Operation(hidden  = true)
	@DeleteMapping("/deletesubscription/{subscriptionId}")
	public ResponseEntity<String> deleteSubscription(@PathVariable long subscriptionId) {
		String updateSubscriptionDetails = subscriptionDetailsService.deleteSubscriptionDetails(subscriptionId);
		return new ResponseEntity<String>(updateSubscriptionDetails, HttpStatus.OK);
	}

}
