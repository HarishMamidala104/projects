package com.ojas.hiring.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.HireType;
import com.ojas.hiring.repo.HireTypeRepo;

@Service
//@Author Rajendar.Baswaraju
public class HireTypeService {
	@Autowired
	HireTypeRepo hireTypeRepo;

	public HireType saveHireType(HireType hireType) {

		return hireTypeRepo.save(hireType);

	}

	public List<HireType> getAll() {
		List<HireType> hireType = new ArrayList<>();
		return hireTypeRepo.findAll();

	}

	public void delete(int id) {
		hireTypeRepo.deleteById(id);
	}

	public void update(int id, HireType hireType) {

		Optional<HireType> rows = hireTypeRepo.findById(id);
		HireType hireTypeadd = new HireType();
		if (rows.isPresent()) {
			hireTypeadd.setHireType(hireType.getHireType());
		}
		hireTypeRepo.saveAndFlush(hireTypeadd);
	}

	public HireType getRecordById(int id) {

		Optional<HireType> rows = hireTypeRepo.findById(id);
		HireType hireTypeadd = new HireType();
		if (rows.isPresent()) {
			return rows.get();
		} else {
			return null;
		}
	}
	// HireType h = rows.orElseThrow(()->new RuntimeException("Row not found"));

	public ResponseEntity<Object> updateEmployee(HireType emp, int id) {

		Optional<HireType> findId = hireTypeRepo.findById(id);

		if (findId.isPresent()) {
			return ResponseEntity.badRequest().body(" Given ID is not exit or Already deleted");
		} else {
			HireType db = hireTypeRepo.findById(id).get();

			if (emp.getHireType() == null) {
				String name = findId.get().getHireType();
				db.setHireType(name);
			} else {
				db.setHireType(emp.getHireType());
			}
			hireTypeRepo.saveAndFlush(db);
			return new ResponseEntity<Object>(db, HttpStatus.OK);
		}
	}
}