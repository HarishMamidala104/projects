package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.ScheduleInterviewTypeDto;
import com.ojas.hiring.repo.ScheduleInterviewTypeRepository;
import com.ojas.hiring.service.InterviewTypeService;

@Service
public class InterviewTypeServiceImpl implements InterviewTypeService {

	@Autowired
	private ScheduleInterviewTypeRepository repository;

	@Override
	public List<ScheduleInterviewTypeDto> getAllInterviewTypes() {
		return repository.findAll();
	}

}
