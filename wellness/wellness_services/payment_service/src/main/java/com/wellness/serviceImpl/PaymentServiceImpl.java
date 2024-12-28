package com.wellness.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellness.entities.PaymentDetails;
import com.wellness.enums.PaymentType;
import com.wellness.exceptions.PaymentIdNotFoundException;
import com.wellness.payload.PaymentDetailsDTO;
import com.wellness.repo.PaymentDetailsRepo;
import com.wellness.service.PaymentService;
@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	PaymentDetailsRepo paymentRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public String paymentCreation(PaymentDetails paymentDetails) {
		String message="failed the payment creation";
		PaymentDetails details = paymentRepo.save(paymentDetails);
		if(details!=null) {
			message="payment creation sucess.";
		}
		return message;
	}

	@Override
	public PaymentDetailsDTO getUserDetails(long paymentId) {
		PaymentDetails payment=null;
		payment=paymentRepo.findById(paymentId)
				.orElseThrow(()-> new PaymentIdNotFoundException("Payment not done by id :"+paymentId));
		PaymentDetailsDTO payDto=modelMapper.map(payment, PaymentDetailsDTO.class);
		return payDto;
	}


}

