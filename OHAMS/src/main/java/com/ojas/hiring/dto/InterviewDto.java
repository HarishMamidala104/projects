package com.ojas.hiring.dto;

import java.util.Date;

public class InterviewDto {

    private long id;
    private long cid;
    private String interviewOn;
    private String interviewRound;
    private String interviewerName;
    private String interviewType;


    private String interviewStatus;

    private String scheduledOn;

    private String teamsLink;
    private String interviewerGmail;
    private String rating;
    private String remarks;

    // Getters
    public long getId() {
        return id;
    }

    public long getCid() {
        return cid;
    }

    public String getInterviewOn() {
        return interviewOn;
    }

    public String getInterviewRound() {
        return interviewRound;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public String getScheduledOn() {
        return scheduledOn;
    }

    public String getTeamsLink() {
        return teamsLink;
    }

    public String getInterviewerGmail() {
        return interviewerGmail;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setInterviewOn(String interviewOn) {
        this.interviewOn = interviewOn;
    }

    public void setInterviewRound(String interviewRound) {
        this.interviewRound = interviewRound;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public void setScheduledOn(String scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public void setTeamsLink(String teamsLink) {
        this.teamsLink = teamsLink;
    }

    public void setInterviewerGmail(String interviewerGmail) {
        this.interviewerGmail = interviewerGmail;
    }

    public String getInterviewStatus() {
        return interviewStatus;
    }

    public void setInterviewStatus(String interviewStatus) {
        this.interviewStatus = interviewStatus;
    }

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
