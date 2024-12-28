package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.FeedbackStatusDTO;

@Repository
public class FeedbackStatusRepository {

	
	@Autowired
    private EntityManager entityManager;

    public List<FeedbackStatusDTO> findAll() {
    	String nativeQuery = "SELECT  DISTINCT  id, feedback_status FROM config_feedback_status";
        Query query = entityManager.createNativeQuery(nativeQuery);
        List<Object[]> resultList = query.getResultList();
        List<FeedbackStatusDTO> list = new ArrayList<>();
        for (Object[] obj : resultList) {
        	FeedbackStatusDTO source = new FeedbackStatusDTO();
            source.setId((Integer) obj[0]);
            source.setFeedbackStatus((String) obj[1]);
            list.add(source);
        }
        return list;
    }
}
