package com.ojas.hiring.service;

import java.util.List;
import java.util.Optional;

import com.ojas.hiring.entity.InterviewFeedback;

public interface InterviewFeedbackService {

	public InterviewFeedback createEmployee(InterviewFeedback interviewFeedback);

	public List<InterviewFeedback> getAllInterviewFeedback();

	public Optional<InterviewFeedback> getInterviewFeedbackById(long id);

	public InterviewFeedback updateInterviewFeedback(long id);

	public void deleteInterviewFeedback(Long id);
	
	public String saveStatus(InterviewFeedback feedback);

}
