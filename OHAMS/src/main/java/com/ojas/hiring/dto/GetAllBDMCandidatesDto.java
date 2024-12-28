package com.ojas.hiring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetAllBDMCandidatesDto {

    @JsonProperty("id")
    private Long rrfId;

    @JsonProperty("job_description")
    private String jobDescription;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("primary_skills")
    private String primarySkills;

    @JsonProperty("hiring_type")
    private String hiringType;

    @JsonProperty("job_level")
    private String jobLevel;

    @JsonProperty("min_exp")
    private Double minExp;

    @JsonProperty("max_exp")
    private Double maxExp;

    @JsonProperty("budget")
    private Double budget;

    @JsonProperty("secondary_skills")
    private String secondarySkills;

    @JsonProperty("email")
    private String emailAddress;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("job_type")
    private String jobType;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("mode_of_work")
    private String modeOfWork;

    @JsonProperty("title")
    private String title;

    @JsonProperty("owner_of_requirement")
    private String ownerOfRequirement;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("location")
    private String location;

    @JsonProperty("experience")
    private String experience;

    @JsonProperty("requirement_Name")
    private String requirementName;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("open_positions")
    private int openPositions;

    @JsonProperty("closed_positions")
    private int closedPositions;

    @JsonProperty("candidate_count")
    private int candidateCount;

    @JsonProperty("published_on")
    private String publishedOn;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("total_positions")
    private int totalPositions;

    @JsonProperty("requirement_status")
    private String requirementStatus;
    @JsonProperty("candidateDetails")
    private List<CandidateDto> candidates;

    @Override
    public String toString() {
        return "GetAllCandidatesDto{" +
                "rrfId=" + rrfId +
                ", candidates=" + candidates +
                '}';
    }

    public Long getRrfId() {
        return rrfId;
    }

    public List<CandidateDto> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDto> candidates) {
        this.candidates = candidates;
    }

    public void setRrfId(Long rrfId) {
        this.rrfId = rrfId;
    }


}
