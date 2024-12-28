package com.ojas.hiring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.hiring.entity.Roles;

public interface RoleRepo extends JpaRepository<Roles, Integer> {
	

}
