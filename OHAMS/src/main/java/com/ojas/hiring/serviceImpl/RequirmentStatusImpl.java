package com.ojas.hiring.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.RequirmentStatusDto;

import com.ojas.hiring.repo.RequirmentStatusRepository;
import com.ojas.hiring.service.RequirmentStatusService;
@Service
public class RequirmentStatusImpl implements RequirmentStatusService{
    @Autowired
	private RequirmentStatusRepository requirmentstatusrepo;

	@Override
	public List<RequirmentStatusDto> getAllSources() {
		// TODO Auto-generated method stub
		return requirmentstatusrepo.findAll();
	}
	

}
