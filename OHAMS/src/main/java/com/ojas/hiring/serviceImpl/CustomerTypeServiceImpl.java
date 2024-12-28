package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.Exception.BadRequestException;
import com.ojas.hiring.dto.CandidateDetailsSource;
import com.ojas.hiring.dto.CustomerTypeDto;
import com.ojas.hiring.repo.CustomerTypeRepository;
import com.ojas.hiring.service.CustomerTypeService;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {

	@Autowired
	private CustomerTypeRepository repository;
	
	@Override
	public List<CustomerTypeDto> findAllCustomerTypes() {
		List<CustomerTypeDto>  list = repository.findAll();
		if(list != null )
		return list;
		else {
			throw  new BadRequestException("404","not Found", null);
		}
		
	}

}
