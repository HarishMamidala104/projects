package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.InterviewStages;

public interface InterviewModeRepo extends JpaRepository<InterviewStages, Integer>{

}
