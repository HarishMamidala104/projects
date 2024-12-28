package com.ojas.hiring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ojas.hiring.dto.CandidateDetailsDto;
import com.ojas.hiring.dto.CandidateDto;
import com.ojas.hiring.dto.GetAllCandidatesDto;
import com.ojas.hiring.dto.GetAllCandidatesDtoInfo;
import com.ojas.hiring.entity.AnalyticalDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.serviceImpl.GetAllCandidatesDtoOne;


public interface CandidateService {

	Candidate updateCandidate(String cid,Candidate candidate);
	
	Candidate getCandidateById(long cid);
	
	Candidate saveCandidate(Candidate candidate);
	
	ResponseEntity<String> getInterviewsByCustomerAndDates(LocalDate startDate, LocalDate endDate);

      List<AnalyticalDto> getInterviewsByStatusAndDates(LocalDate startDate, LocalDate endDate);
	
      ResponseEntity<String> getInterviewsByTechnologyAndDates(LocalDate startDate, LocalDate endDate);
      
      
      
      Map<String, List<GetAllCandidatesDtoInfo>> getBDMDetails();
      
      Map<String, List<CandidateDetailsDto>> getCandidateDetails();

      
     // Map<String, List<CandidateDetailsDto>> getCandidateDetailsDto();
}
