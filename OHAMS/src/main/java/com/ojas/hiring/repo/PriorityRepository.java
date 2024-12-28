package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ojas.hiring.dto.PriorityDto;
@Repository
public class PriorityRepository {

	@Autowired
	private EntityManager entityManager;
	 
	    public List<PriorityDto> findAll() {
	    	String nativeQuery = "SELECT  DISTINCT  id, priorityType FROM config_priority_information;";
	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> resultList = query.getResultList();
	        List<PriorityDto> list = new ArrayList<>();
	        for (Object[] obj : resultList) {
	        	PriorityDto prioritytype = new PriorityDto();
	        	prioritytype.setId((Integer) obj[0]);
	        	prioritytype.setPriorityType((String) obj[1]);
	            list.add(prioritytype);
	        }
	        return list;
	    }
	
}
