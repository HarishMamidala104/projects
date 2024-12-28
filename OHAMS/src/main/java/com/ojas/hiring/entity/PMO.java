package com.ojas.hiring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "data_pmo")
public class PMO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pmoId;

	@Column(name = "EMPLOYEE_ID", columnDefinition = "MEDIUMTEXT")
	private String employeeId; // Employee ID

	@Column(name = "EMPLOYEE_NAME", columnDefinition = "MEDIUMTEXT")
	private String employeeName; // Username

	@Column(name = "EMPLOYEE_TYPE", columnDefinition = "MEDIUMTEXT")
	private String employeeType; // Employee Type

	@Column(name = "PROJECT_CATAGORY", columnDefinition = "MEDIUMTEXT")
	private String projectCategory; // Project Category

	@Column(name = "CLIENT_NAME", columnDefinition = "MEDIUMTEXT")
	private String clientName; // Client Name

	@Column(name = "PROJECT_NAME", columnDefinition = "MEDIUMTEXT")
	private String projectName; // Project Name

	@Column(name = "PROJECT_STATUS", columnDefinition = "MEDIUMTEXT")
	private String projectStatus; // Project Status

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	@Column(name = "PROJECT_START_DATE", columnDefinition = "MEDIUMTEXT")
	private String projectStartDate; // Project Start Date

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	@Column(name = "PROJECT_END_DATE", columnDefinition = "MEDIUMTEXT")
	private String projectEndDate; // Project End Date

	@Column(name = "DEPARTMENT", columnDefinition = "MEDIUMTEXT")
	private String department;

	@Column(name = "NETWORKING_DAYS", columnDefinition = "MEDIUMTEXT")
	private String netWorkingDays; // NetWorking Days

	@Column(name = "REMARKS", columnDefinition = "MEDIUMTEXT")
	private String remarks;

	@Column(name = "optionalSheetNameOfExcel", columnDefinition = "MEDIUMTEXT")
	private String optionalSheetNameOfExcel;

	private Date publishedOn;

	public String getOptionalSheetNameOfExcel() {
		return optionalSheetNameOfExcel;
	}

	public void setOptionalSheetNameOfExcel(String optionalSheetNameOfExcel) {
		this.optionalSheetNameOfExcel = optionalSheetNameOfExcel;
	}

	public Date getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}

	public int getPmoId() {
		return pmoId;
	}

	public void setPmoId(int pmoId) {
		this.pmoId = pmoId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getNetWorkingDays() {
		return netWorkingDays;
	}

	public void setNetWorkingDays(String netWorkingDays) {
		this.netWorkingDays = netWorkingDays;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
