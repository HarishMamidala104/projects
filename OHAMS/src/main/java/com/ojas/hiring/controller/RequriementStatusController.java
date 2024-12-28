package com.ojas.hiring.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.RequirmentStatusDto;
import com.ojas.hiring.serviceImpl.RequirmentStatusImpl;


@RestController
@RequestMapping("/api")
public class RequriementStatusController {
	
	
	@Autowired
 private  RequirmentStatusImpl service;
	 
		@GetMapping("/requirmentstatus")
		public ResponseEntity<List<RequirmentStatusDto>> getAllSource() {
			List<RequirmentStatusDto> allSources = service.getAllSources();
			return ResponseEntity.ok(allSources);
	 
		}
}
