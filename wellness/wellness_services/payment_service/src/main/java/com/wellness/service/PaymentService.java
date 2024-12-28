package com.wellness.service;

import com.wellness.entities.PaymentDetails;
import com.wellness.enums.PaymentType;
import com.wellness.payload.PaymentDetailsDTO;

public interface PaymentService {

	public String paymentCreation(PaymentDetails paymentDetails);
	
	public PaymentDetailsDTO getUserDetails(long paymentId);
}
