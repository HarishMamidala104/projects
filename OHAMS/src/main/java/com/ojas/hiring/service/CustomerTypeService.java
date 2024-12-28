package com.ojas.hiring.service;

import java.util.List;

import com.ojas.hiring.dto.CustomerTypeDto;

public interface CustomerTypeService {

	
	List<CustomerTypeDto> findAllCustomerTypes();
}
