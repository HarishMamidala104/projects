package com.ojas.hiring.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ojas.hiring.entity.HireTable;

public interface HireTableService {

	public HireTable addInterviewDetails(HireTable hireTable);

	public List<HireTable> getAllInterviewDetails();

	public ResponseEntity<Object> findInterviewById(int hid);

	public ResponseEntity<Object> findByInterviewByHireId(String hireId);

	public ResponseEntity<Object> findByInterviewByToken(String hid);

	public ResponseEntity<Object> updateHireeDetails(HireTable hireTable, int hid);

	public ResponseEntity<Object> deleteHiree(HireTable hireTable, int hid);

}