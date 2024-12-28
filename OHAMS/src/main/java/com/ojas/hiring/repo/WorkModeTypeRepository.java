package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ojas.hiring.dto.WorkModeDto;

@Repository
public class WorkModeTypeRepository {
	@Autowired
	private EntityManager entityManager;
	 
	    public List<WorkModeDto> findAll() {
	    	String nativeQuery = "SELECT  DISTINCT  id, workmode_type FROM config_modeofwork_information";
	        Query query = entityManager.createNativeQuery(nativeQuery);
	        List<Object[]> resultList = query.getResultList();
	        List<WorkModeDto> list = new ArrayList<>();
	        for (Object[] obj : resultList) {
	        	WorkModeDto source = new WorkModeDto();
	            source.setId((Integer) obj[0]);
	            source.setWorkModeType((String) obj[1]);
	            list.add(source);
	        }
	        return list;
	    }

}
