package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.JobTypeDto;


@Repository
public class JobTypeRepository {
	@Autowired
	private EntityManager entityManager;
	 
	    public List<JobTypeDto> findAll() {
	    	String nativeQuery = "SELECT  DISTINCT  id, job_type FROM config_jobtype_information;";
	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> resultList = query.getResultList();
	        List<JobTypeDto> list = new ArrayList<>();
	        for (Object[] obj : resultList) {
	        	JobTypeDto jobtype = new JobTypeDto();
	        	jobtype.setId((Integer) obj[0]);
	        	jobtype.setJobType((String) obj[1]);
	            list.add(jobtype);
	        }
	        return list;
	    }

}
