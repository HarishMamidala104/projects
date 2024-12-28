package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.ExperienceDto;
import com.ojas.hiring.repo.ExperienceRepository;
import com.ojas.hiring.service.ExperienceService;
@Service
public class ExperienceServiceImpl implements ExperienceService {
	@Autowired
	private ExperienceRepository experiencerepo;

	@Override
	public List<ExperienceDto> getAllExperiences() {
		
		return experiencerepo.findAll();
	}

}
