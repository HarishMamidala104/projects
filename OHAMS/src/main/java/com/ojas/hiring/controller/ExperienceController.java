package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.ExperienceDto;
import com.ojas.hiring.serviceImpl.ExperienceServiceImpl;


@RestController
@RequestMapping("/api")
public class ExperienceController {
	@Autowired
	private ExperienceServiceImpl service;
	
	@GetMapping("/experience")
	public ResponseEntity<List<ExperienceDto>> getAllJobTypes() {
		List<ExperienceDto> allexperiences = service.getAllExperiences();
		return ResponseEntity.ok(allexperiences);
 
	}
}
