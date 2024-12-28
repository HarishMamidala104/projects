package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.hiring.dto.PriorityDto;
import com.ojas.hiring.serviceImpl.PriorityServiceImpl;

@RestController
@RequestMapping("/api")
public class PriorityController {
	 @Autowired
		private PriorityServiceImpl service;
		
		@GetMapping("/priorities")
		public ResponseEntity<List<PriorityDto>> getAllJobTypes() {
			List<PriorityDto> allpriorities = service.getAllPriorities();
			return ResponseEntity.ok(allpriorities);
	 
		}

}
