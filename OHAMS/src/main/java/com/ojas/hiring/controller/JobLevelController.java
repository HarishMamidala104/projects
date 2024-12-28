package com.ojas.hiring.controller;
//
//import java.util.HashMap;

//import java.util.Map;
//import java.util.Optional;
//

//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.web.bind.annotation.DeleteMapping;

//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojas.hiring.entity.JobLevel;
import com.ojas.hiring.repo.JobLevelRepo;
import com.ojas.hiring.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@RestController
@RequestMapping("/api")
public class JobLevelController {

	@Autowired
	JobLevelRepo jobLevelRepo;

	@Autowired
	JobLevelService jobLevelService;
//
//	@PostMapping("/postJobLevelDetails")
//	public String postJobLevel(@RequestBody JobLevel jobLevel) {
//		JobLevel save = jobLevelRepo.save(jobLevel);
//		return "Successfully created joblevel";
//
//	}
//
	@GetMapping("/getJobLevelDetails")
	public List<JobLevel> getJobLevelDetails() {
		List<JobLevel> findAll = jobLevelRepo.findAll();
		return findAll;

}
}
//
//	@GetMapping("/getById/{id}")
//	public ResponseEntity<JobLevel> getById(@PathVariable Integer id) {
//		Optional<JobLevel> findById = jobLevelRepo.findById(id);
//		Map<String, Object> dataMap = new HashMap<>();
//		if (findById.isEmpty()) {
//			dataMap.put("message", "No records found");
//			return new ResponseEntity(dataMap, HttpStatus.OK);
//		}
//		dataMap.put("jobLevel record ById", findById);
//		return new ResponseEntity(dataMap, HttpStatus.OK);
//
//	}
//
//	@PutMapping("/updateById/{id}")
//	public String updateById(@PathVariable int id, @RequestBody JobLevel jobLevel) {
//		Optional<JobLevel> findById = jobLevelRepo.findById(id);
//		if (findById.isPresent()) {
//			JobLevel jobLevel2 = findById.get();
//			if (jobLevel2.getJobLevel() != null) {
//				jobLevel2.setJobLevel(jobLevel.getJobLevel());
//			}
//			jobLevelRepo.save(jobLevel2);
//			return "Successfully updated";
//		}
//		return "No record found";
//	}
//	
//	@DeleteMapping("/deleteJobById/{id}")
//	public String deleteJobLevelById(@PathVariable int id) {
//		jobLevelRepo.deleteById(id);
//		return "Successfully record deleted";
//		
//	}
//}
