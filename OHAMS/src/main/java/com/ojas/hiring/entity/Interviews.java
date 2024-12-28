package com.ojas.hiring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "data_interviews_schedule")
public class Interviews implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
//	private long cid;
	private String interviewOn;// Java, DotNet, PHP
	private String interviewRound;// Technical-Round-1,Technical-Round-2,Manager-Round,HR-Round,HiringManager-Round
	private String interviewerName;
	private String interviewType;// F2F,Online
	private String candidateNotification;// Please make arrangements for code test.
	private String interviewerNotification;// Candidate is 15 years experienced, please evaluate thoroughly
	private Date scheduledOn;
	private String interviewStatus;// Scheduled,Taken,Not-Taken,Postponed,Cancelled
	private String interviewStatusComment;
//	private Date publishedOn;
	private String teamsLink;// Interview teams link
	private String interviewerGmail;// Interviewer GmailId
	private String requirementName;
	private long rrfId;
	private long employeeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    @JsonManagedReference
//	@JsonBackReference
    private Candidate candidate;

	@Transient
	@OneToMany
	private List<CandidateInterviewStagesHistory> candidateInterviewStagesHistory;
	
	@Transient
	@OneToMany
	private List<InterviewFeedback> interviewFeedbacks;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public long getCid() {
//		return cid;
//	}
//
//	public void setCid(long cid) {
//		this.cid = cid;
//	}

	public String getInterviewOn() {
		return interviewOn;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setInterviewOn(String interviewOn) {
		this.interviewOn = interviewOn;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getInterviewRound() {
		return interviewRound;
	}

	public void setInterviewRound(String interviewRound) {
		this.interviewRound = interviewRound;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}

	public String getCandidateNotification() {
		return candidateNotification;
	}

	public void setCandidateNotification(String candidateNotification) {
		this.candidateNotification = candidateNotification;
	}

	public String getInterviewerNotification() {
		return interviewerNotification;
	}

	public void setInterviewerNotification(String interviewerNotification) {
		this.interviewerNotification = interviewerNotification;
	}

	public Date getScheduledOn() {
		return scheduledOn;
	}

	public void setScheduledOn(Date scheduledOn) {
		this.scheduledOn = scheduledOn;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getInterviewStatusComment() {
		return interviewStatusComment;
	}

	public void setInterviewStatusComment(String interviewStatusComment) {
		this.interviewStatusComment = interviewStatusComment;
	}

//	public Date getPublishedOn() {
//		return publishedOn;
//	}
//
//	public void setPublishedOn(Date publishedOn) {
//		this.publishedOn = publishedOn;
//	}

	public String getTeamsLink() {
		return teamsLink;
	}

	public void setTeamsLink(String teamsLink) {
		this.teamsLink = teamsLink;
	}

	public String getInterviewerGmail() {
		return interviewerGmail;
	}

	public void setInterviewerGmail(String interviewerGmail) {
		this.interviewerGmail = interviewerGmail;
	}

	public List<CandidateInterviewStagesHistory> getCandidateInterviewStagesHistory() {
		return candidateInterviewStagesHistory;
	}

	public void setCandidateInterviewStagesHistory(List<CandidateInterviewStagesHistory> candidateInterviewStagesHistory) {
		this.candidateInterviewStagesHistory = candidateInterviewStagesHistory;
	}

	public List<InterviewFeedback> getInterviewFeedbacks() {
		return interviewFeedbacks;
	}

	public void setInterviewFeedbacks(List<InterviewFeedback> interviewFeedbacks) {
		this.interviewFeedbacks = interviewFeedbacks;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public long getRrfId() {
		return rrfId;
	}

	public void setRrfId(long rrfId) {
		this.rrfId = rrfId;
	}
	
}