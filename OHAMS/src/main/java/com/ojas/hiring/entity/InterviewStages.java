package com.ojas.hiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class InterviewStages {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	@NotBlank(message = "interview_mode is mandatory")
	private String interview_stage; // interview mode like stage1,stage2,stage3,Manager Round,HR

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterview_stage() {
		return interview_stage;
	}

	public void setInterview_stage(String interview_stage) {
		this.interview_stage = interview_stage;
	}

}
