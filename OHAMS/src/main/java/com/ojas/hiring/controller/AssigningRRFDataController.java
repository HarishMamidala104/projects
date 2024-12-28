package com.ojas.hiring.controller;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.AssignedRRFData;
import com.ojas.hiring.entity.RRF;
import com.ojas.hiring.entity.User;
import com.ojas.hiring.repo.AssignedRRFDataRepo;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.RRFRepo;
import com.ojas.hiring.repo.UserRepository;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")
public class AssigningRRFDataController {

	@Autowired
	RRFRepo rrfRepo;

	@Autowired
	AssignedRRFDataRepo assignedRRFDataRepo;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CandidateRepo candidateRepo;

	@PostMapping("/assignRRFData")
	public String assigningRRFDataToEmployee(@RequestParam List<Long> rrf_id,
			@RequestParam("userName") String userName) {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String date = currentDate.format(ofPattern);
		for (Long rrfId : rrf_id) {
			Optional<RRF> findById = rrfRepo.findById(rrfId);
			RRF rrfData = findById.get();
		
			rrfData.setEmailAddress(userName);
			User userDetails = userRepository.getByUserName(userName);
			rrfData.setEmployeeId(userDetails.getEmployeeId());
			rrfRepo.save(rrfData);
			AssignedRRFData assignedRRFData = new AssignedRRFData();
			assignedRRFData.setRrf_id(rrfId);
			assignedRRFData.setCustomerName(rrfData.getCustomerName());
			assignedRRFData.setExpirence(rrfData.getMinExp());
			assignedRRFData.setOpenPositions(rrfData.getOpenPositions());
			assignedRRFData.setClosedPositions(rrfData.getClosedPositions());
			assignedRRFData.setAssignedTo(userName);
			assignedRRFData.setAssignedOn(date);
			assignedRRFData.setJobRole(rrfData.getJobTitle());
			assignedRRFData.setAssigned_by(null);
			assignedRRFDataRepo.save(assignedRRFData);
			}

		return "Success";

	}

	@GetMapping("/getRRFData")
	public List getRRFData(@RequestParam("employeeId") long employeeId) {
		List<Map<String, Object>> getrrfData = rrfRepo.getrrfData(employeeId);
		return getrrfData;

	}

	@GetMapping("/getAssignedHistory/{rrfId}")
	public List<AssignedRRFData> getAllAssignedHistory(@PathVariable long rrfId) {
		List<AssignedRRFData> getAssignedata = assignedRRFDataRepo.getByrrfId(rrfId);
		return getAssignedata;

	}

}
