package com.ojas.hiring.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ojas.hiring.entity.HireTable;

public interface HireTableRepo extends JpaRepository<HireTable, Integer> {

	@Query(value = "select * FROM data_candidate_interview_levels_information  where hid=?1 and visibility=1 order by published_date desc", nativeQuery = true)
	List<HireTable> findInterviewById(@Param("id") int id);

	@Query(value = "select * FROM data_candidate_interview_levels_information  where hireId=?1 and visibility=1 order by published_date desc", nativeQuery = true)
	List<HireTable> findInterviewByHireId(@Param("id") String hireId);

	@Query(value = "select * FROM data_candidate_interview_levels_information  where token=?1 and visibility=1 order by published_date desc", nativeQuery = true)
	List<HireTable> findInterviewByToken(@Param("id") String id);

}