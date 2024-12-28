package com.ojas.hiring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.HireType;

//@Author Rajendar.Baswaraju
public interface HireTypeRepo extends JpaRepository<HireType, Integer> {

	Optional<HireType> findByHireType(String hireType);

}