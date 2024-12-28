package com.ojas.hiring.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.OwnerOfRequirement;
import com.ojas.hiring.exceptions.RequirementOwnerInvalid;
import com.ojas.hiring.repo.RequirementOwnerRepository;

@RestController
@RequestMapping("/api")
public class OwnerOfRequirementController {

	@Autowired
	private RequirementOwnerRepository ownerRepository;

	@PostMapping("/addOwnerDetails")
	public String addRequirementOwner(@RequestBody OwnerOfRequirement ofRequirement) {

		String findByRequirementOwner = ownerRepository.findByOwnerOfRequirement(ofRequirement.getOwnerOfRequirement());
		if (findByRequirementOwner == null) {
			LocalDate now = LocalDate.now();
			DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("dd MMM, yyyy");
			String format = now.format(ofPattern);
			ofRequirement.setCreatedOn(format);
			ofRequirement.setVisibility(1);
			ownerRepository.save(ofRequirement);
			return "Requirement Owner added Succesfully";
		}
		throw new RequirementOwnerInvalid("Requirement owner is all ready existed .....try with another name ....");
	}

	@GetMapping("/getOwnerDetails")
	public List<Map<String, Object[]>> getOwnerDetails() {
		List<Map<String, Object[]>> allRequierementOwnerDetails = ownerRepository.findAllByVisibility(1);
		return allRequierementOwnerDetails;
	}
	
	@DeleteMapping("/deleteOwnerById")
	public String deleteOwnerById(@RequestParam ("id") Integer id) {
		OwnerOfRequirement ownerDetails = ownerRepository.getOwnerDetails(id);
		ownerDetails.setVisibility(0);
		ownerRepository.save(ownerDetails);
		return "Owner Details Deleted SuccesFully ";
	}
	
	@PutMapping("/updateTheOwnerDetails/{id}")
	public ResponseEntity<String> updateOwnerDetails(@PathVariable int id, @RequestBody OwnerOfRequirement updatedOwner) {
	//	OwnerOfRequirement existingOwner = ownerRepository.findById(id).orElse(null);
		OwnerOfRequirement existingOwner = ownerRepository.getOwnerDetails(id);
		
		if(existingOwner == null) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
		existingOwner.setOwnerOfRequirement(updatedOwner.getOwnerOfRequirement());
	//	existingOwner.setVisibility(updatedOwner.getVisibility());
		OwnerOfRequirement saveOwner = ownerRepository.save(existingOwner);			
		if (saveOwner!=null) {
            return ResponseEntity.ok("Owner updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Owner not found with ID: " + id);
        }
    

		}
}
