package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.dto.WorkModeDto;
import com.ojas.hiring.repo.WorkModeTypeRepository;
import com.ojas.hiring.service.WorkModeTypeService;
@Service
public class WorkModeTypeImpl implements WorkModeTypeService {
 @Autowired
	private WorkModeTypeRepository workmodetyperepo;
	
	@Override
	public List<WorkModeDto> getAllWorkModeTypes() {
	
		return workmodetyperepo.findAll();
	}

}
