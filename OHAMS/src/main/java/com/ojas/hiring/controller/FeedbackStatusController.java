package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.FeedbackStatusDTO;
import com.ojas.hiring.service.FeedbackStatusService;

@RestController
@RequestMapping("/api")
public class FeedbackStatusController {

	@Autowired
	private FeedbackStatusService serviceImpl;

	@GetMapping("/feedbackstatus")
	public ResponseEntity<List<FeedbackStatusDTO>> getAllFeedbacks() {

		List<FeedbackStatusDTO> allFeedbacks = serviceImpl.findAllFeedbacks();

		return ResponseEntity.ok(allFeedbacks);
	}

}
