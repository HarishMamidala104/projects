package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.CandidateSubStatusDto;
import com.ojas.hiring.repo.CandidateSubStatusRepository;
import com.ojas.hiring.service.CandidateSubStatusService;

@Service
public class CandidateSubStatusServiceImpl  implements CandidateSubStatusService {
 
	@Autowired
	private CandidateSubStatusRepository repository;
	
	@Override
	public List<CandidateSubStatusDto> getAllSubStatus(String status) {
		return repository.findByStatus(status);
	}

}
