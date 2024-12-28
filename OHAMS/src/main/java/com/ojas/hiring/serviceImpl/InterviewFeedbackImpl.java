package com.ojas.hiring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.CandidateStatus;
import com.ojas.hiring.entity.CandidateSubStatus;
import com.ojas.hiring.entity.InterviewFeedback;
import com.ojas.hiring.entity.InterviewStage;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.repo.CandidateRepo;
import com.ojas.hiring.repo.InterviewFeedbackRepository;
import com.ojas.hiring.repo.InterviewsRepository;
import com.ojas.hiring.service.InterviewFeedbackService;

@Service
public class InterviewFeedbackImpl implements InterviewFeedbackService {
	
	@Autowired
	private CandidateRepo candidateRepo;
	
	@Autowired
	private InterviewsRepository interviewsRepository;

	@Autowired
	private InterviewFeedbackRepository interviewRepositary;

	@Override
	public InterviewFeedback createEmployee(InterviewFeedback interviewFeedback) {

		return interviewRepositary.save(interviewFeedback);
	}

	@Override
	public List<InterviewFeedback> getAllInterviewFeedback() {
		return interviewRepositary.getInterviewFeedBack();
//		return interviewRepositary.findAll();		
	}

	@Override
	public Optional<InterviewFeedback> getInterviewFeedbackById(long id) {

		return interviewRepositary.findById(id);
	}

	@Override
	public InterviewFeedback updateInterviewFeedback(long id) {

		return null;
	}

	@Override
	public void deleteInterviewFeedback(Long id) {

		interviewRepositary.deleteById(id);
	}
	
	
	@Override
	public String saveStatus(InterviewFeedback feedback) {
		Candidate candidate = candidateRepo.getCandidateByInterviewId(feedback.getInterview_id());
		Interviews interview = interviewsRepository.
				getInterviewByFeedBackId(feedback.getInterview_id());
		String interviewStage = interview.getInterviewRound();
		if(interviewStage.equalsIgnoreCase(InterviewStage.STAGE_1.toString())) {
			 
			if(feedback.getStatus().equalsIgnoreCase("SELECTED")) {
				candidate.setSubStatus(CandidateSubStatus.L1_SELECT.toString());
			}
			else {
				candidate.setSubStatus(CandidateSubStatus.L1_REJECT.toString());
				candidate.setStatus(CandidateStatus.Reject.toString());
			}
		}
		else if(interviewStage.equalsIgnoreCase(InterviewStage.STAGE_2.toString()))  {
			if(feedback.getStatus().equalsIgnoreCase("SELECTED")) {
				candidate.setSubStatus(CandidateSubStatus.L2_SELECT.toString());
			}
			else {
				candidate.setSubStatus(CandidateSubStatus.L2_REJECT.toString());
				candidate.setStatus(CandidateStatus.Reject.toString());
			}
		}
		else if(interviewStage.equalsIgnoreCase(InterviewStage.STAGE_3.toString()) ) {
			if(feedback.getStatus().equalsIgnoreCase("SELECTED")) {
				candidate.setSubStatus(CandidateSubStatus.L3_SELECT.toString());
			}
			else {
				candidate.setSubStatus(CandidateSubStatus.L3_REJECT.toString());
				candidate.setStatus(CandidateStatus.Reject.toString());
			}
		}
		else if(interviewStage.equalsIgnoreCase(InterviewStage.MANAGER.toString()))
				if(feedback.getStatus().equalsIgnoreCase("SELECTED")) {
					candidate.setSubStatus(CandidateSubStatus.MANAGER_SELECT.toString());
				}
				else {
					candidate.setSubStatus(CandidateSubStatus.MANAGER_REJECT.toString());
					candidate.setStatus(CandidateStatus.Reject.toString());
		}
		else if(interviewStage.equalsIgnoreCase(InterviewStage.MANAGER.toString()))
			if(feedback.getStatus().equalsIgnoreCase("SELECTED")) {
				candidate.setSubStatus(CandidateSubStatus.CLIENT_SELECT.toString());
			}
			else {
				candidate.setSubStatus(CandidateSubStatus.CLIENT_REJECT.toString());
				candidate.setStatus(CandidateStatus.Reject.toString());
	}
		candidateRepo.save(candidate);
		return "Candidate SubStatus Sent SUCCESSFULLY.....";
	}

}
