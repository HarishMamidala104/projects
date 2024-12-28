package com.ojas.hiring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojas.hiring.entity.InterviewStages;
import com.ojas.hiring.repo.InterviewModeRepo;

@RestController
@RequestMapping("/api")
public class InterviewModeController {

	@Autowired
	InterviewModeRepo interviewModeRepo;

	@PostMapping("/postInterviewModeDetails")
	public String postInterviewMode(@RequestBody InterviewStages interviewStages) {
		InterviewStages save = interviewModeRepo.save(interviewStages);
		return "successfully created InterviewMode";

	}

	@GetMapping("/getAllInterviewStageDetails")
	public List<InterviewStages> getAllDetails() {
		List<InterviewStages> findAll = interviewModeRepo.findAll();
		return findAll;

	}

	@GetMapping("/getInterviewStageById/{id}")
	public ResponseEntity getBYId(@PathVariable int id) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<InterviewStages> findById = interviewModeRepo.findById(id);
		if (findById.isPresent()) {
			dataMap.put("message", findById);
			return new ResponseEntity(dataMap, HttpStatus.OK);

		}
		dataMap.put("message", "No record found");
		return new ResponseEntity(dataMap, HttpStatus.OK);

	}

	@PutMapping("/updateInterviewStageById/{id}")
	public String updateById(@PathVariable int id, @RequestBody InterviewStages interviewStages) {
		Optional<InterviewStages> findById = interviewModeRepo.findById(id);
		if (findById.isPresent()) {
			InterviewStages interviewStages2 = findById.get();
			if (interviewStages2.getInterview_stage() != null) {
				interviewStages2.setInterview_stage(interviewStages.getInterview_stage());
			}
			interviewModeRepo.save(interviewStages2);
			return "record updated successfully";
		}
		return "No record found";

	}

	@DeleteMapping("/deleteInterviewStageById/{id}")
	public String deleteByid(@PathVariable int id) {
		Optional<InterviewStages> findById = interviewModeRepo.findById(id);
		if (findById.isPresent()) {
			return "Record deleted successfully";
		}
		return "no records found";

	}
}