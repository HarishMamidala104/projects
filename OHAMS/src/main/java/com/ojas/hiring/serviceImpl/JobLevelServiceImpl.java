package com.ojas.hiring.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.JobLevel;
import com.ojas.hiring.repo.JobLevelRepo;
import com.ojas.hiring.service.JobLevelService;

@Service
public class JobLevelServiceImpl implements JobLevelService {

	@Autowired
	JobLevelRepo jobLevelRepo;

	@Override
	public JobLevel findById(int id, JobLevel joblevel) {
		JobLevel byId = jobLevelRepo.getById(id);
		if (byId != null) {
			if (byId.getJobLevel() != null) {
				byId.setJobLevel(joblevel.getJobLevel());
			}
		}
		return jobLevelRepo.save(byId);
	}

	@Override
	public List<JobLevel> getAllJobLevel() {
		
		return jobLevelRepo.findAll();
	}

}
