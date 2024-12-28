package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ojas.hiring.dto.ExperienceDto;


@Repository
public class ExperienceRepository {
	@Autowired
	private EntityManager entityManager;
	 
	    public List<ExperienceDto> findAll() {
	    	String nativeQuery = "SELECT  DISTINCT  id, experience FROM  config_experience_information;";
	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> resultList = query.getResultList();
	        List<ExperienceDto> list = new ArrayList<>();
	        for (Object[] obj : resultList) {
	        	ExperienceDto experience = new ExperienceDto();
	        	experience.setId((Integer) obj[0]);
	        	experience.setExperience((String) obj[1]);
	            list.add(experience);
	        }
	        return list;
	    }
}
