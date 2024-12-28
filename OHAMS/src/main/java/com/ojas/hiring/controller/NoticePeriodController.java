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

import com.ojas.hiring.entity.NoticePeriod;
import com.ojas.hiring.repo.NoticePeriodRepo;

@RestController
@RequestMapping("/api")
public class NoticePeriodController {

	@Autowired
	NoticePeriodRepo noticePeriodRepo;

	@PostMapping("/postNoticePeriod")
	public String postNoticePeriod(@RequestBody NoticePeriod noticePeriod) {
		NoticePeriod save = noticePeriodRepo.save(noticePeriod);
		return "Successfully record created";

	}

	@GetMapping("/getAllNoticeperioddetails")
	public List<NoticePeriod> getAll() {
		List<NoticePeriod> findAll = noticePeriodRepo.findAll();
		return findAll;

	}

	@GetMapping("/getNoticePeriodById/{id}")
	public ResponseEntity<NoticePeriod> getbyId(@PathVariable int id) {
		Map<String, Object> datamap = new HashMap<>();
		Optional<NoticePeriod> findById = noticePeriodRepo.findById(id);
		if (findById.isEmpty()) {
			datamap.put("message", "No Record found");
			return new ResponseEntity(datamap, HttpStatus.OK);
		}
		datamap.put("message", findById);
		return new ResponseEntity(datamap, HttpStatus.OK);

	}

	@PutMapping("/updateNoticePeriodById/{id}")
	public String updateNoticePeriod(@PathVariable int id, @RequestBody NoticePeriod noticePeriod) {
		Optional<NoticePeriod> findById = noticePeriodRepo.findById(id);
		if (findById.isPresent()) {
			NoticePeriod noticePeriod2 = findById.get();
			if (noticePeriod2.getNoticeperiod() > 0) {
				noticePeriod2.setNoticeperiod(noticePeriod.getNoticeperiod());
			}
			NoticePeriod save = noticePeriodRepo.save(noticePeriod2);
			return "Record updated successfully";
		}
		return "No record found";

	}
	
	@DeleteMapping("deleteById/{id}")
	public String deleteById(@PathVariable int id) {
		noticePeriodRepo.deleteById(id);
		return "Successfully record deleted ";
		
	}

}
