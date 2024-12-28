package com.ojas.hiring.service;

import java.util.List;

import com.ojas.hiring.dto.CandidateSubStatusDto;

public interface CandidateSubStatusService {

	List<CandidateSubStatusDto> getAllSubStatus(String status);

}
