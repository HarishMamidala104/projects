package com.ojas.hiring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.HireType;
import com.ojas.hiring.entity.Positions;

@Repository
//@Author Rajendar.Baswaraju
public interface PositionsRepo extends JpaRepository<Positions, Integer> {

	Optional<Positions> findById(int id);

	void saveAndFlush(HireType hireTypeadd);

}