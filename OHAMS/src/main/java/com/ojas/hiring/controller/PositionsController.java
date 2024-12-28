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

import com.ojas.hiring.entity.HireType;
import com.ojas.hiring.entity.Positions;
import com.ojas.hiring.serviceImpl.PositionsService;

@RestController
@RequestMapping("/api")
public class PositionsController {
	@Autowired
	PositionsService positionsService;

	@PostMapping("/positions")
	private Positions saveData(@RequestBody Positions positions) {
		return positionsService.savePositions(positions);

	}

	@GetMapping("/positions")
	private List<Positions> getData() {
		return positionsService.getAllPositions();

	}

	@DeleteMapping("/positions/{position_id}")
	private void deleteData(@PathVariable("position_id") Integer position_id) {
		positionsService.deletePosistions(position_id);
	}

	@PutMapping(value = "/positions/{id}")
	public Object update(@RequestBody Positions positions, @PathVariable int id) {
		ResponseEntity<Object> update = positionsService.update(positions, id);
		return new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}
	
	@GetMapping("/createPositionsData")
	private void createHireTypesData() {
		if (positionsService.getAllPositions().size() == 0) {
			Positions positions = new Positions(1, "L1");
			positionsService.savePositions(positions);
			positions = new Positions(2, "L2");
			positionsService.savePositions(positions);
			positions = new Positions(3, "L3");
			positionsService.savePositions(positions);
			positions = new Positions(4, "L4");
			positionsService.savePositions(positions);
			positions = new Positions(5, "L5");
			positionsService.savePositions(positions);
		}
		System.out.println("Positions Records Count : " + positionsService.getAllPositions().size());
	}
}