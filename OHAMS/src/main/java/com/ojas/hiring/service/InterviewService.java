package com.ojas.hiring.service;

import java.util.Date;
import java.util.List;

import com.ojas.hiring.entity.CandidateDTO;
import com.ojas.hiring.entity.InterviewDto;
import com.ojas.hiring.entity.Interviews;

public interface InterviewService {
	// public Interviews createInterview(Interviews interview);
	public List<Interviews> getAllInterviews();

	public Interviews getInterviewById(Long id);

	public Interviews updateInterview(Interviews interview);

	public void deleteInterview(Long id);

	public Interviews createInterview(Interviews interview);

	public String getAllInterviewsAndInterviewScedule();

	public List<Interviews> getInterviewsByCandidateId(Long id);

	public void updateInterviewStatus(long intId, String overallFeedback);


	List<CandidateDTO> getAllInterviewsWithCandidateName();

	//List<InterviewDto> getAllInterviewsWithCandidates();
}
