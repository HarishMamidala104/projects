package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.PrimarySkills;
import com.ojas.hiring.repo.PrimarySkillsRepo;
import com.ojas.hiring.service.PrimaryService;

@RestController
@RequestMapping("/api")
public class PrimarySkillsController {

	@Autowired
	PrimarySkillsRepo primarySkillsRepo;
	@Autowired
	PrimaryService primaryService;

	@PostMapping("/addPrimarySkills")
	public String postPrimarySkills(@RequestBody PrimarySkills primarySkills) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
		String format = now.format(ofPattern);
		primarySkills.setCreatedOn(format);
		primarySkills.setVisibility(1);
		primarySkillsRepo.save(primarySkills);
		return "PrimarySkill Successfully created";
	}

	@GetMapping("/getAllPrimarySkills")
	public List<PrimarySkills> getAllPrimarySkills() {
		 List<PrimarySkills> allPrimarySkills = primarySkillsRepo.getAllPrimarySkills(1);
		return allPrimarySkills;
	}
	
	@DeleteMapping("/deletePrimarySkillByid")
	public String deletePrimarySkillByid(@RequestParam("id") Integer id) {
		PrimarySkills bySkillId = primarySkillsRepo.getBySkillId(id);
		bySkillId.setVisibility(0);
		primarySkillsRepo.save(bySkillId);
		 return "Successfully Deleted";
	}
	

    @PutMapping("/updateTechnology/{id}")
    public ResponseEntity<String> updateTechnology(@PathVariable(value = "id") int id, @RequestBody PrimarySkills primarySkills) {
        try {
        	boolean isUpdated =  primaryService.updatedPrimarySkills(id, primarySkills);
            if (isUpdated) {
                return ResponseEntity.ok("Technology updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technology not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the Technology.");
        }
    }
}
	
	


