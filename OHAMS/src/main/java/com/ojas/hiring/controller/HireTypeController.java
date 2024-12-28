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
import com.ojas.hiring.serviceImpl.HireTypeService;

@RestController
@RequestMapping("/api")
//@Author Rajendar.Baswaraju
public class HireTypeController {
	@Autowired
	HireTypeService hireTypeService;

	@PostMapping(value = "/hireTypes")
	private HireType saveData(@RequestBody HireType hireType) {
		return hireTypeService.saveHireType(hireType);

	}

	@GetMapping("/hireTypes")
	private List<HireType> getData() {
		return hireTypeService.getAll();

	}

	@DeleteMapping("/hireTypes/{hire_id}")
	private void delete(@PathVariable("hire_id") int hire_id) {
		hireTypeService.delete(hire_id);
	}

	@PutMapping(value = "/hireTypes/{id}")
	public Object updatesHire(@RequestBody HireType hireType, @PathVariable int id) {
		ResponseEntity<Object> update = hireTypeService.updateEmployee(hireType, id);
		return new ResponseEntity<Object>(update, HttpStatus.ACCEPTED).getBody();
	}

	@GetMapping("/createHireTypesData")
	private void createHireTypesData() {
		if (hireTypeService.getAll().size() == 0) {
			HireType hireType = new HireType(1, "Internal");
			if (hireTypeService.getRecordById(1) == null) {
				hireTypeService.saveHireType(hireType);
			}
			hireType = new HireType(2, "External");
			if (hireTypeService.getRecordById(2) == null) {
				hireTypeService.saveHireType(hireType);
			}
		}
		System.out.println("HireType Records Count : " + hireTypeService.getAll().size());
	}

}