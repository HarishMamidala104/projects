package com.ojas.hiring.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.Customer;
import com.ojas.hiring.repo.CustomersRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomersRepo customerRepository;

	public Customer updateCustomer(int id, Customer updatedCustomer) {
		Optional<Customer> optionalCustomer = customerRepository.getId(id);
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		if (optionalCustomer.isPresent()) {
			Customer existingCustomer = optionalCustomer.get();
			existingCustomer.setCustomer_name(updatedCustomer.getCustomer_name());
			existingCustomer.setType(updatedCustomer.getType());
			//existingCustomer.setCreatedOn(format);
			//existingCustomer.setVisibility(updatedCustomer.getVisibility());

			return customerRepository.save(existingCustomer);
		} else {
			throw new RuntimeException("Customer not found with id: " + id);
		}
	}
}