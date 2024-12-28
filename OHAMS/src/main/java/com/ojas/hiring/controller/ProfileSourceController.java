package com.ojas.hiring.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.ProfileSource;
import com.ojas.hiring.serviceImpl.ProfileSourceService;

@RestController
@RequestMapping("/api/profilesource")
//@Author Rajendar.Baswaraju
public class ProfileSourceController {
	@Autowired
	ProfileSourceService profileSourceService;

	@PostMapping("/addprofile")
	private ResponseEntity<Object> saveData(@RequestBody ProfileSource profileSource) {
		return profileSourceService.saveProfileSource(profileSource);
	}

	@GetMapping("/detailsall")
	private List<ProfileSource> getData() {
		// List<ProfileSource> list =new ArrayList<ProfileSource>();
		return profileSourceService.getAllById();

	}

	@DeleteMapping("/detete/{profile_id}")
	private void deleteData(@PathVariable("profile_id") int profile_id) {
		profileSourceService.deleteProfileSource(profile_id);
	}

	@PutMapping(value = "/update/{id}")
	public Object updateEmployee(@RequestBody ProfileSource profileSource, @PathVariable(value = "id") int id) {
		ResponseEntity<Object> update = profileSourceService.profileupdate(profileSource, id);
		return new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}
}