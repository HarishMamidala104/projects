package com.ojas.hiring.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "data_interviews_feedback")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class InterviewFeedback implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	
//	@JsonIgnore
	private long interview_id;

	private String primarySkill;
	@ElementCollection
	@CollectionTable(name = "data_interviews_feedback_scores")
	private Map<String, Integer> skillRatings;

	@ElementCollection
	@CollectionTable(name = "data_interviews_feedback_details")
	private Map<String, String> skillFeedbacks;

	private String overallRating;
	private String overallFeedback;
	private String remarks;
	private String status;
	private Date publishedOn;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInterview_id() {
		return interview_id;
	}
	public void setInterview_id(long interview_id) {
		this.interview_id = interview_id;
	}
	public String getPrimarySkill() {
		return primarySkill;
	}
	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}
	public Map<String, Integer> getSkillRatings() {
		return skillRatings;
	}
	public void setSkillRatings(Map<String, Integer> skillRatings) {
		this.skillRatings = skillRatings;
	}
	public Map<String, String> getSkillFeedbacks() {
		return skillFeedbacks;
	}
	public void setSkillFeedbacks(Map<String, String> skillFeedbacks) {
		this.skillFeedbacks = skillFeedbacks;
	}
	
	public String getOverallFeedback() {
		return overallFeedback;
	}
	public void setOverallFeedback(String overallFeedback) {
		this.overallFeedback = overallFeedback;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPublishedOn() {
		return publishedOn;
	}
	public void setPublishedOn(Date publishedOn) {
		this.publishedOn = publishedOn;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(String overallRating) {
		this.overallRating = overallRating;
	}
	
}