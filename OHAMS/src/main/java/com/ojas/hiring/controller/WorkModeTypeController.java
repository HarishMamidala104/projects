package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.hiring.dto.WorkModeDto;
import com.ojas.hiring.serviceImpl.WorkModeTypeImpl;


@RestController
@RequestMapping("/api")
public class WorkModeTypeController {
	@Autowired
	private WorkModeTypeImpl service;
	
	@GetMapping("/workmodetype")
	public ResponseEntity<List<WorkModeDto>> getAllSource() {
		List<WorkModeDto> allworkmodetypes = service.getAllWorkModeTypes();
		return ResponseEntity.ok(allworkmodetypes);
 
	}

}
