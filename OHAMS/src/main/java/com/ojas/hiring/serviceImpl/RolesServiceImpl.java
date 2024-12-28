package com.ojas.hiring.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.Roles;
import com.ojas.hiring.repo.RoleRepo;
import com.ojas.hiring.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {
	@Autowired
	private RoleRepo repository;

	@Override
	public List<Roles> findAllRoles() {
		return repository.findAll();
	}

}
