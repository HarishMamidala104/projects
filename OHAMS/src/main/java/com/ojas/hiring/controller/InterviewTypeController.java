package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.ScheduleInterviewTypeDto;
import com.ojas.hiring.service.InterviewTypeService;

@RestController
@RequestMapping("/api")
public class InterviewTypeController {
	@Autowired
	private InterviewTypeService interviewTypeService;

	@GetMapping("/intervietypes")
	public ResponseEntity<List<ScheduleInterviewTypeDto>> getAllInterviewTypes() {
		List<ScheduleInterviewTypeDto> list = interviewTypeService.getAllInterviewTypes();
		return ResponseEntity.ok(list);
	}

}
