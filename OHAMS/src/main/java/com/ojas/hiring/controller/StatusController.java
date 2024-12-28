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

import com.ojas.hiring.entity.Status;
import com.ojas.hiring.repo.StatusRepo;

@RestController
@RequestMapping("/api")
public class StatusController {

	@Autowired
	StatusRepo statusRepo;

	@PostMapping("/poststatus")
	public String postStatus(@RequestBody Status status) {
		Status save = statusRepo.save(status);
		return "Successfully created status";

	}

	@GetMapping("/getStatusDetails")
	public List<Status> getStatusDetails() {
		List<Status> findAll = statusRepo.findAll();
		return findAll;

	}

	@GetMapping("/getStatusDetailsById/{id}")
	public ResponseEntity<Status> getStatusDetailsById(@PathVariable int id) {
		Map<String, Object> datamap = new HashMap<>();
		Optional<Status> findById = statusRepo.findById(id);
		if (findById.isEmpty()) {
			datamap.put("message", "No Record found");
			return new ResponseEntity(datamap, HttpStatus.OK);
		}
		datamap.put("message", findById);
		return new ResponseEntity(datamap, HttpStatus.OK);

	}

	@PutMapping("/updateStatus/{id}")
	public String updateStatus(@PathVariable int id, @RequestBody Status status) throws Exception {
		Optional<Status> findById = statusRepo.findById(id);
		if (findById.isPresent()) {
			Status status2 = findById.get();
				if (status2.getStatus() != null) {
					status2.setStatus(status.getStatus());
				}
			Status save = statusRepo.save(status2);
			return "Record Successfully updated";
		}
		return "No Record found";
	}
	
	@DeleteMapping("/deleteStatusById/{id}")
	public String deleteStatusById(@PathVariable int id) {
		statusRepo.deleteById(id);
		return "Successfully record deleted";
		
	}

}
