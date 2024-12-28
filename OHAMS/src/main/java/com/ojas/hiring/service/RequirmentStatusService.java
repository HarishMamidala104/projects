package com.ojas.hiring.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.RequirmentStatusDto;


@Service
public interface RequirmentStatusService {
	List<RequirmentStatusDto>  getAllSources();
}
