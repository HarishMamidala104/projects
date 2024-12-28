package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.CandidateDetailsSource;
import com.ojas.hiring.service.CandidateDetailsSourceService;

@RestController
@RequestMapping("/api")
public class CandidateDetailsSourceController {

	@Autowired
	private CandidateDetailsSourceService service;

	@GetMapping("/sourcedtails")
	public ResponseEntity<List<CandidateDetailsSource>> getAllSource() {
		List<CandidateDetailsSource> allSources = service.getAllSources();
		
		return ResponseEntity.ok(allSources);

	}

}
