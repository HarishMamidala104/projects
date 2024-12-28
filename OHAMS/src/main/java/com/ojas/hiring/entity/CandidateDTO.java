package com.ojas.hiring.entity;


public class CandidateDTO {
    private Long cid;
    private String fullName;
    private String emailId;
    private String currentlyWorkingAs;
    private String currentlyWorkingAt;
    private String currentLocation;
//    private Long rrf;
    private InterviewDto interviewDto;
    private RrfDTO rrfdto;
    private String status;
    private String subStatus;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCurrentlyWorkingAs() {
        return currentlyWorkingAs;
    }

    public void setCurrentlyWorkingAs(String currentlyWorkingAs) {
        this.currentlyWorkingAs = currentlyWorkingAs;
    }

    public String getCurrentlyWorkingAt() {
        return currentlyWorkingAt;
    }

    public void setCurrentlyWorkingAt(String currentlyWorkingAt) {
        this.currentlyWorkingAt = currentlyWorkingAt;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

//    public Long getRrf() {
//        return rrf;
//    }
//
//    public void setRrf(Long rrf) {
//        this.rrf = rrf;
//    }

    public InterviewDto getInterviewDto() {
        return interviewDto;
    }

    public void setInterviewDto(InterviewDto interviewDto) {
        this.interviewDto = interviewDto;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public RrfDTO getRrfdto() {
		return rrfdto;
	}

	public void setRrfdto(RrfDTO rrfdto) {
		this.rrfdto = rrfdto;
	}
    
}
