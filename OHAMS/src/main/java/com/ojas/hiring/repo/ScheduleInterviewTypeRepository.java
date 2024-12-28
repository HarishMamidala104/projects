package com.ojas.hiring.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ojas.hiring.dto.ScheduleInterviewTypeDto;

@Repository
public class ScheduleInterviewTypeRepository {

	@Autowired
	private EntityManager entityManager;

	public List<ScheduleInterviewTypeDto> findAll() {

		
		String nativeQuery = "SELECT * FROM config_interview_type";
		Query query = entityManager.createNativeQuery(nativeQuery);
		List<Object[]> resultList = query.getResultList();
		List<ScheduleInterviewTypeDto> list = new ArrayList<>();
		for (Object[] obj : resultList) {
			ScheduleInterviewTypeDto source = new ScheduleInterviewTypeDto();
			source.setId((Integer) obj[0]);
			source.setInterviewType((String) obj[1]);
			list.add(source);
		}
		return list;

	}

}
