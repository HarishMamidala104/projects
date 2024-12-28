package com.ojas.hiring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.Interviewer;
import com.ojas.hiring.entity.Locations;

public interface LocationRepo extends JpaRepository<Locations, Integer> {
	@Query(value="SELECT * FROM location where location = ?1 And visibility =1 ",nativeQuery = true)
	public String findByLocationname(String name);

	@Query(value = "Select * from location Where visibility =1 ORDER BY id DESC",nativeQuery = true)
	public List<Locations> getAllLocations();
	
	
	@Query(value = "SELECT * FROM location where id = ?1",nativeQuery = true)
	public Locations getLocation(int id);
}


