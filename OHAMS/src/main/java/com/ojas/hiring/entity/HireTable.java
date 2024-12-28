package com.ojas.hiring.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "data_candidate_interview_levels_information")
/* @Author RavindranathGV */

public class HireTable {

	private int hid;
	@NotBlank(message = "HireId is mandatory")
	private String hireId;
	@NotBlank(message = "Feedback is mandatory")
	private String comment;
	@NotBlank(message = "Interviewer Name is mandatory")
	private String interviewer;
	private String roleDescription;
	@Range(min = 1, max = 5, message = "Please Specify Rating")
	private int rating;
	@NotBlank(message = "Selection Status is mandatory")
	private String status;
	private java.sql.Date publishedDate;
	private String token;
	private int visibility;

	public HireTable(int hid, @NotBlank(message = "HireId is mandatory") String hireId,
			@NotBlank(message = "Feedback is mandatory") String comment,
			@NotBlank(message = "Tech Interviewer Name is mandatory") String interviewer, String roleDescription,
			@NotNull(message = "Please Specify Rating") int rating,
			@NotBlank(message = "Selection Status is mandatory") String status, Date publishedDate, String token,
			int visibility) {
		super();
		this.hid = hid;
		this.hireId = hireId;
		this.comment = comment;
		this.interviewer = interviewer;
		this.roleDescription = roleDescription;
		this.rating = rating;
		this.status = status;
		this.publishedDate = publishedDate;
		this.token = token;
		this.visibility = visibility;
	}

	@Column(columnDefinition = "smallint default 1")
	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hid", unique = true, nullable = false)
	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	@Column(name = "hire_id")
	public String getHireId() {
		return hireId;
	}

	public void setHireId(String hireId) {
		this.hireId = hireId;
	}

	@Column(name = "comment", columnDefinition = "MEDIUMTEXT")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "interviewer")
	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	@Column(name = "role_description")
	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(name = "rating")
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "published_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public java.sql.Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(java.sql.Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	@Column(name = "token", columnDefinition = "TINYTEXT")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "HireTable [hid=" + hid + ", hireId=" + hireId + ", comment=" + comment + ", interviewer=" + interviewer
				+ ", roleDescription=" + roleDescription + ", rating=" + rating + ", status=" + status
				+ ", publishedDate=" + publishedDate + ", token=" + token + ", visibility=" + visibility + "]";
	}

	public HireTable() {

	}

}