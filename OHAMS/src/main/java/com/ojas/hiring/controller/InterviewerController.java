package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.Interviewer;
import com.ojas.hiring.entity.OwnerOfRequirement;
import com.ojas.hiring.entity.PrimarySkills;
import com.ojas.hiring.entity.Vendor;
import com.ojas.hiring.exceptions.InvalidInterviewer;
import com.ojas.hiring.repo.InterviewerRepository;

@RestController
@RequestMapping("/api")
public class InterviewerController {

	@Autowired
	InterviewerRepository interviewerRepo;


	@PostMapping("/addInterviewerDetails")
	public String postInterviewerDetails(@RequestBody  Interviewer interviewer) {
		if (interviewerRepo.findByInterviewerName(interviewer.getInterviewer()) != null) {
			throw new InvalidInterviewer("Interviewer already exists...Please try with another Interviewer");
		}
		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		interviewer.setCreatedOn(format);
		interviewer.setVisibility(1);
		interviewerRepo.save(interviewer);

		return "Interviewer added successfully";
	}
	@GetMapping("/getInteviewerDetails")
	public List<Map<String, Object[]>> getIntervieweDetails() {
		List<Map<String, Object[]>> allInterviewerDetails = interviewerRepo.getAllInterviewerDetails(1);
		return allInterviewerDetails;
	}
	
	@SuppressWarnings("deprecation")
	@DeleteMapping("/deleteIntrviewerById")
		public String deleteVendorById(@RequestParam("id") Integer id) {
		 Interviewer interviewer = interviewerRepo.getById(id);
		 interviewer.setVisibility(0);
		interviewerRepo.save(interviewer);
		return "Interviewer Successfully Deleted";
	}
	
	
	@PutMapping("/updateInterview/{id}")
	public ResponseEntity<?> updateInterview(@PathVariable int id, @RequestBody Interviewer updateInterviewer) {
	    Interviewer existingInterviewer = interviewerRepo.getInterviewDetails(id);

	    if (existingInterviewer == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interviewer not found with ID: " + id);
	    }

	    // Update the fields of the existing interviewer
	    existingInterviewer.setInterviewer(updateInterviewer.getInterviewer());
	    existingInterviewer.setInterviewerGmail(updateInterviewer.getInterviewerGmail());

	    Interviewer savedInterviewer = interviewerRepo.save(existingInterviewer);

	    // If the save operation returns null or fails for any reason, treat it as not found.
	    if (savedInterviewer != null) {
	        return ResponseEntity.ok("Interviewer updated successfully.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interviewer not found with ID: " + id);
	    }
	}
}