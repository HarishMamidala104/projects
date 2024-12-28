package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.CustomerTypeDto;
import com.ojas.hiring.service.CustomerTypeService;

@RestController
@RequestMapping("/api")
public class CustomerTypeController {

	@Autowired
	private CustomerTypeService  service;
	
	@GetMapping("/customertype")
	public ResponseEntity<List<CustomerTypeDto>> getAllCustomerTypes(){
		List<CustomerTypeDto> customerTypes = service.findAllCustomerTypes(); 
		
		return ResponseEntity.ok(customerTypes);
	}
}
