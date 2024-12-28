package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.hiring.dto.JobTypeDto;
import com.ojas.hiring.serviceImpl.JobTypeServiceImpl;

@RestController
@RequestMapping("/api")
public class JobTypeController {
    @Autowired
	private JobTypeServiceImpl service;
	
	@GetMapping("/jobtype")
	public ResponseEntity<List<JobTypeDto>> getAllJobTypes() {
		List<JobTypeDto> alljobtypes = service.getAllJobTypes();
		return ResponseEntity.ok(alljobtypes);
 
	}
	
}
