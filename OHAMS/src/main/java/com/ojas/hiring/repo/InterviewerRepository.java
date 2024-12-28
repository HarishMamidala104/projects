package com.ojas.hiring.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.entity.Interviewer;
import com.ojas.hiring.entity.OwnerOfRequirement;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer,Integer> {
	
	@Query(value="SELECT * FROM interviewer_reference where visibility = ?1 ORDER BY id DESC ",nativeQuery = true)
	public List<Map<String,Object[]>> getAllInterviewerDetails(int visibility);

	@Query(value="SELECT * FROM interviewer_reference WHERE Interviewer = ?1 AND visibility = 1",nativeQuery = true)
	public Interviewer findByInterviewerName(String name);

	@Query(value = "SELECT * FROM interviewer_reference where id = ?1",nativeQuery = true)
	public Interviewer getInterviewDetails(int id);
}
