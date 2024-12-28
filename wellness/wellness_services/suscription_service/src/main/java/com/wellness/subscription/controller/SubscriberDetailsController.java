package com.wellness.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.subscription.entities.SubscriberDetails;
import com.wellness.subscription.payloads.SubscribersDetailsDTO;
import com.wellness.subscription.service.SubscriberDetailsService;

@RestController
@RequestMapping("/wellness/plan")
@CrossOrigin(origins = "*")
public class SubscriberDetailsController {

	@Autowired
	private SubscriberDetailsService detailsService;

	@PostMapping("/subscribe")
	public ResponseEntity<String> createSubscription(@RequestBody SubscriberDetails details) {
		String createSubscription = detailsService.createSubscription(details);
		return new ResponseEntity<String>(createSubscription, HttpStatus.CREATED);
	}
	
	@GetMapping("/viewallsubscribers")
	public ResponseEntity<List<SubscribersDetailsDTO>> viewAllSubscribers(){
		List<SubscribersDetailsDTO> viewAllSubscriberDetails = detailsService.viewAllSubscriberDetails();
		
		return new ResponseEntity<List<SubscribersDetailsDTO>>(viewAllSubscriberDetails,HttpStatus.OK);
	}
	
	
	
}
