package com.ojas.hiring.entity;

import jdk.jfr.Unsigned;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Interviewer_reference")
public class Interviewer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	
	@NotBlank(message="interviewer is mandatory")
	@Column(unique = true)
	private String interviewer;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "interviewer_gmail")
	private String interviewerGmail;
	
	private int visibility;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public String getInterviewerGmail() {
		return interviewerGmail;
	}

	public void setInterviewerGmail(String interviewerGmail) {
		this.interviewerGmail = interviewerGmail;
	}

	
}
