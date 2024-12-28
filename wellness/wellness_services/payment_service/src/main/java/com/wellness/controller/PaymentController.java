package com.wellness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellness.entities.PaymentDetails;
import com.wellness.service.PaymentService;



@RestController
@RequestMapping("/wellness")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	@GetMapping("/hi")
	public String hiController() {
		return "Hi wellness";
	}
	
	@PostMapping("/payment")
	public ResponseEntity<String> createSubscription(@RequestBody PaymentDetails paymentDetails) {
		String createPayment = paymentService.paymentCreation(paymentDetails);
		return new ResponseEntity<>(createPayment, HttpStatus.CREATED);
	}
}
