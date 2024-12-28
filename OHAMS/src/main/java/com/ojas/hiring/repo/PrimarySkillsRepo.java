package com.ojas.hiring.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.PrimarySkills;

public interface PrimarySkillsRepo extends JpaRepository<PrimarySkills, Integer> {

	@Query(value = "select * from primary_skills where visibility = ?1 ORDER BY id DESC", nativeQuery = true)
	public List<PrimarySkills> getAllPrimarySkills(int visibility);
	
	@Query(value = "select * from primary_skills where id=?1",nativeQuery = true )
	public PrimarySkills getBySkillId(int id);
	
	@Query(value = "select * from primary_skills where primary_skills=?1",nativeQuery = true )
	public String findByPrimarySkill(String name);
	
	@Query(value="select * from primary_skills where id=?1",nativeQuery = true)
	 Optional<PrimarySkills>   getId(long id);
	
}
