package com.ojas.hiring.service;

import java.util.List;


import com.ojas.hiring.entity.JobLevel;

public interface JobLevelService {
	public JobLevel findById(int id,JobLevel joblevel);
	List<JobLevel>  getAllJobLevel();
}
