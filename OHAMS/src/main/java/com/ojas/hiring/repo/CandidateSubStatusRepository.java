package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.CandidateSubStatusDto;
@Repository
public class CandidateSubStatusRepository {
	
	
	@Autowired
    private EntityManager entityManager;
	
	public List<CandidateSubStatusDto> findByStatus(String status) {
	    String nativeQuery;
	    Query query;

	    // If the status is "Active" or "InProgress", no need to set a parameter
	    if (status.equals("Active") || status.equals("InProgress")) {
	        nativeQuery = "SELECT id, sub_status FROM candidate_sub_status WHERE status IN ('Active')";
	        query = entityManager.createNativeQuery(nativeQuery);
	    } else {
	        // For other statuses, the :status parameter will be set
	        nativeQuery = "SELECT id, sub_status FROM candidate_sub_status WHERE status = :status";
	        query = entityManager.createNativeQuery(nativeQuery);
	        query.setParameter("status", status);
	    }

	    List<Object[]> resultList = query.getResultList();
	    List<CandidateSubStatusDto> list = new ArrayList<>();
	    for (Object[] obj : resultList) {
	        CandidateSubStatusDto dto = new CandidateSubStatusDto();
	        dto.setId((Integer) obj[0]);
	        dto.setSubstatus((String) obj[1]);
	        list.add(dto);
	    }
	    return list;
	}

}
