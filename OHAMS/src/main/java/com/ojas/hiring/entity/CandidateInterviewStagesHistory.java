package com.ojas.hiring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "candidate_interview_stages_history")
public class CandidateInterviewStagesHistory {

	@Id
	@GeneratedValue
	private int id;
	private long cid;
	@Column(name = "interviewer_name")
	private String interviewerName;
	@Column(name = "interviewer_round")
	private String interviewRound;
	@Column(name = "interview_status")
	private String interviewStatus;

	private long schedule_interview_id;
	
	@Column(name = "scheduled_on")
	private Date scheduledOn;
	
	@Column(name = "interviewer_gmail")
	private String interviewerGmail;

	public CandidateInterviewStagesHistory() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getInterviewRound() {
		return interviewRound;
	}

	public void setInterviewRound(String interviewRound) {
		this.interviewRound = interviewRound;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public long getSchedule_interview_id() {
		return schedule_interview_id;
	}

	public void setSchedule_interview_id(long schedule_interview_id) {
		this.schedule_interview_id = schedule_interview_id;
	}

	public Date getScheduledOn() {
		return scheduledOn;
	}

	public void setScheduledOn(Date scheduledOn) {
		this.scheduledOn = scheduledOn;
	}

	public String getInterviewerGmail() {
		return interviewerGmail;
	}

	public void setInterviewerGmail(String interviewerGmail) {
		this.interviewerGmail = interviewerGmail;
	}

	
}
