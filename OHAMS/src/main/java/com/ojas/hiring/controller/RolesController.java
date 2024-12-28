package com.ojas.hiring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.hiring.entity.Roles;
import com.ojas.hiring.service.RolesService;

@RestController
@RequestMapping("/api")
public class RolesController {

	@Autowired
	private RolesService serviceImpl;

	@GetMapping("/roles")
	public ResponseEntity<List<Roles>> getAllRoles() {
		List<Roles> list = serviceImpl.findAllRoles();

		return ResponseEntity.ok(list);
	}
}
