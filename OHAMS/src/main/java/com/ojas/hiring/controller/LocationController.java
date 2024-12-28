package com.ojas.hiring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.dto.LocationsDTO;
import com.ojas.hiring.service.LocationService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping("/addLocation")
	public ResponseEntity<String> addLocationDetails(@RequestBody @Valid LocationsDTO locations) {

		String result = locationService.addLocationDetails(locations);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getAllLocationDetails")
	public ResponseEntity<List<LocationsDTO>> getLocationDetails() {
		List<LocationsDTO> locations = locationService.getLocationDetails();
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@GetMapping("/getAllLocationDetailsPaginated")
	public ResponseEntity<List<LocationsDTO>> getLocationDetails(@RequestParam int offset, @RequestParam int limit,
			@RequestParam String sortby) {
		List<LocationsDTO> locations = locationService.getLocationDetails(offset, limit, sortby);
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@DeleteMapping("deleteLocationById")
	public ResponseEntity<String> deleteLocation(@RequestParam Integer id) {
		String result = locationService.deleteLocation(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/updateLocation/{id}")
	public ResponseEntity<String> updateLocation(@PathVariable int id,
			@RequestBody @Valid LocationsDTO updateLocations) {

		String respons = locationService.updateLocation(id, updateLocations);

		return ResponseEntity.ok(respons);

	}
}
