package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.JobTypeDto;
import com.ojas.hiring.repo.JobTypeRepository;
import com.ojas.hiring.service.JobTypeService;
@Service
public class JobTypeServiceImpl implements JobTypeService {
	@Autowired
  private JobTypeRepository jobtyperepo;
	@Override
	public List<JobTypeDto> getAllJobTypes() {
		// TODO Auto-generated method stub
		return jobtyperepo.findAll();
	}

}
