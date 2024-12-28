package com.ojas.hiring.repo;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.RequirmentStatusDto;

@Repository
public class RequirmentStatusRepository  {
	@Autowired
	private EntityManager entityManager;
	 
	    public List<RequirmentStatusDto> findAll() {
	    	String nativeQuery = "SELECT  DISTINCT  requirementstatus_id, status_type FROM config_requriementstatus_information";
	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> resultList = query.getResultList();
	        List<RequirmentStatusDto> list = new ArrayList<>();
	        for (Object[] obj : resultList) {
	        	RequirmentStatusDto source = new RequirmentStatusDto();
	            source.setRequirementstatus_id((Integer) obj[0]);
	            source.setStatus_type((String) obj[1]);
	            list.add(source);
	        }
	        return list;
	    }

}
