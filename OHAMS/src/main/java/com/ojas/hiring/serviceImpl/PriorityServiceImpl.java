package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.PriorityDto;
import com.ojas.hiring.repo.PriorityRepository;
import com.ojas.hiring.service.PriorityService;
@Service
public class PriorityServiceImpl implements PriorityService {
	@Autowired
	private PriorityRepository priorityrepo;

	@Override
	public List<PriorityDto> getAllPriorities() {
		
		return priorityrepo.findAll();
	}

}
