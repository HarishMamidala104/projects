package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.JobLevel;

public interface JobLevelRepo extends JpaRepository<JobLevel, Integer> {


	public JobLevel getById(int id);
}
