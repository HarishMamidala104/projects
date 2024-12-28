package com.ojas.hiring.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.OwnerOfRequirement;

@Repository
public interface RequirementOwnerRepository extends JpaRepository<OwnerOfRequirement,Integer> {
	
	@Query(value="SELECT * FROM owner_of_requirement where owner_of_requirement = ?1 And visibility = 1 ",nativeQuery = true)
    public String findByOwnerOfRequirement(String name);
    
	@Query(value="SELECT * FROM owner_of_requirement where visibility = ?1 ORDER BY id DESC",nativeQuery = true)
    public List<Map<String, Object[]>> findAllByVisibility(int visibility);
	
	@Query(value = "SELECT * FROM owner_of_requirement where id = ?1",nativeQuery = true)
	public OwnerOfRequirement getOwnerDetails(int id);
    
}
