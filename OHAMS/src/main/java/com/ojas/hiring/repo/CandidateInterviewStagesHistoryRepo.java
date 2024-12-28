package com.ojas.hiring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojas.hiring.entity.CandidateInterviewStagesHistory;

public interface CandidateInterviewStagesHistoryRepo extends JpaRepository<CandidateInterviewStagesHistory, Integer>{

	public List<CandidateInterviewStagesHistory> getByCid(long cid);
	
	@Query(value = "SELECT * FROM candidate_interview_stages_history where schedule_interview_id=?1 \r\n"
			+ "AND interviewer_round = ?2",nativeQuery = true)
	public CandidateInterviewStagesHistory getByscheduleinterviewidANDinterviewRound(long schedule_interview_id,String interviewRound);
	
	
}
