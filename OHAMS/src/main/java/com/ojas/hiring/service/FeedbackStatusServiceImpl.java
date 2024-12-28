package com.ojas.hiring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.dto.FeedbackStatusDTO;
import com.ojas.hiring.repo.FeedbackStatusRepository;

@Service
public class FeedbackStatusServiceImpl implements FeedbackStatusService{

	@Autowired
	private FeedbackStatusRepository repository;
	
	@Override
	public List<FeedbackStatusDTO> findAllFeedbacks() {
		
		List<FeedbackStatusDTO> feedbackList = repository.findAll();
		if(feedbackList != null)
		
		return feedbackList;
		else {
			throw  new BadRequestException("404","not Found", null);
		}
	}

}
