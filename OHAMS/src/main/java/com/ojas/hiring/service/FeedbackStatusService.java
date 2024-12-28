package com.ojas.hiring.service;

import java.util.List;

import com.ojas.hiring.dto.FeedbackStatusDTO;

public interface FeedbackStatusService {

	List<FeedbackStatusDTO> findAllFeedbacks();
}
