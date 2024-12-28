package com.ojas.hiring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.HireType;
import com.ojas.hiring.entity.Positions;
import com.ojas.hiring.repo.PositionsRepo;

@Service
//@Author Rajendar.Baswaraju
public class PositionsService {
	@Autowired
	PositionsRepo positionsRepo;

	public Positions savePositions(Positions positions) {

		return positionsRepo.save(positions);

	}

	public List<Positions> getAllPositions() {
//	List<Positions> list=new ArrayList<Positions>();
		return positionsRepo.findAll();
	}

	public void deletePosistions(int id) {
		positionsRepo.deleteById(id);

	}

	public void update1(Positions positions, int id) {

		Optional<Positions> rows = positionsRepo.findById(id);
		HireType hireTypeadd = new HireType();
		if (rows.isPresent()) {
			hireTypeadd.setHireType(positions.getPositionname());
		}
		positionsRepo.saveAndFlush(hireTypeadd);
	}

	public ResponseEntity<Object> update(Positions emp, int id) {

		Optional<Positions> findId = positionsRepo.findById(id);

		if (findId.isPresent()) {
			return ResponseEntity.badRequest().body(" Given ID is not exit or Already deleted");
		} else {
			Positions db = positionsRepo.findById(id).get();

			if (emp.getPositionname() == null) {
				String name = findId.get().getPositionname();
				db.setPositionname(name);
			} else {
				db.setPositionname(emp.getPositionname());

			}
			positionsRepo.saveAndFlush(db);
			return new ResponseEntity<Object>(db, HttpStatus.OK);
		}

	}

}