package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.Customer;
import com.ojas.hiring.repo.CustomersRepo;
import com.ojas.hiring.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomersRepo customersRepo;
	@Autowired
	private CustomerService customerService;

	@PostMapping("/addCustomerDetails")
	public String addCustomerDetails(@RequestBody Customer customer) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		customer.setCreatedOn(format);
		customer.setVisibility(1);
		customersRepo.save(customer);
		return "Successfully added customer data";
	}

	@GetMapping("/getAllCustomers")
	public List<Map<String, Object[]>> getAllCustomers() {
		List<Map<String, Object[]>> customerData = customersRepo.getCustomerData(1);
		return customerData;
	}

	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam("id") Integer id) {
		Customer customer = customersRepo.getById(id);
		customer.setVisibility(0);
		customersRepo.save(customer);
		return "Successfully Deleted";
	}

	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<String> updateCustomer(@PathVariable("id") int id, @RequestBody Customer customer) {

		Customer updateCustomer = customerService.updateCustomer(id, customer);
		if (updateCustomer == null)
			return ResponseEntity.ok("Customer updated successfully.");

		else

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + id);

	}

}
