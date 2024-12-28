package com.ojas.hiring.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "assigned_rrf_data")
public class AssignedRRFData {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Long rrf_id;
	
	private String customerName;
	
	private int openPositions;
	
	private int closedPositions;
	
	public double expirence;
	
	@Column(name = "job_role")
	private String jobRole;
	
	private String assigned_by;
	
	private String assignedOn;
	
	private String assignedTo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Long getRrf_id() {
		return rrf_id;
	}

	public void setRrf_id(Long rrf_id) {
		this.rrf_id = rrf_id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getOpenPositions() {
		return openPositions;
	}

	public void setOpenPositions(int openPositions) {
		this.openPositions = openPositions;
	}

	public int getClosedPositions() {
		return closedPositions;
	}

	public void setClosedPositions(int closedPositions) {
		this.closedPositions = closedPositions;
	}

	public double getExpirence() {
		return expirence;
	}

	public void setExpirence(double expirence) {
		this.expirence = expirence;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getAssigned_by() {
		return assigned_by;
	}

	public void setAssigned_by(String assigned_by) {
		this.assigned_by = assigned_by;
	}


	public AssignedRRFData() {
		super();
	}

	public String getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	
}
