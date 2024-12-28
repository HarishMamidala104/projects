package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.dto.CandidateDetailsSource;
import com.ojas.hiring.repo.CandidateDetailsSourceRepository;
import com.ojas.hiring.service.CandidateDetailsSourceService;

@Service
public class CandidateDetailsSourceServiceImpl implements CandidateDetailsSourceService {

	@Autowired
	private CandidateDetailsSourceRepository repository;

	@Override
	public List<CandidateDetailsSource> getAllSources() {
		List<CandidateDetailsSource>  list = repository.findAll();
		if(list != null )
		return list;
		else {
			throw  new BadRequestException("404","not Found", null);
		}
		
	}

}
