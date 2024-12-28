package com.ojas.hiring.serviceImpl;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Builder
@Data
public class GetAllCandidatesDtoOne {
    private Long rrfId;
    private String customerName;
    private String primarySkills;
    private String hiringType;
    private String jobLevel;
    private Integer minExp;
    private Integer maxExp;
    private Double budget;
    private String secondarySkills;
    private Long employeeId;
    private String jobTitle;
    private String jobType;
    private Integer priority;
    private String modeOfWork;
    private String title;
    private String ownerOfRequirement;
    private String city;
    private String state;
    private String location;
    private Integer experience;
    private String requirementName;
    private Integer visibility;
    private Integer openPositions;
    private Integer closedPositions;
    private Integer candidateCount;
    private LocalDate publishedOn;
    private String createdBy;
    private Integer totalPositions;
    private String requirementStatus;
    private Long candidateId;
    private String fullName;
    private String mobileNo;
    private String emailId;
    private Integer totalExperience;
    private String currentlyWorkingAs;
    private String currentlyWorkingAt;
    private String currentLocation;
    private String preferredLocation;
    private Double expectedCtcPa;
    private Integer noticePeriod;
    private Boolean currentlyServingNoticePeriod;
    private String comments;
    private String availability;
    private Long candidateEmployeeId;
    private String resourceType;
    private String candidateRequirementName;
    private String rrfLink;
    private String status;
    private Double offeredCTC;
    private Integer candidateVisibility;
    private String recordAuthor;
    private String ipAddress;
    private String vendor;
    private LocalDate creationDate;
    private String source;
    private String subStatus;
    private String additionalSkills;

    public GetAllCandidatesDtoOne(Long rrfId, String customerName, String primarySkills, String hiringType, String jobLevel, Integer minExp, Integer maxExp, Double budget, String secondarySkills, Long employeeId, String jobTitle, String jobType, Integer priority, String modeOfWork, String title, String ownerOfRequirement, String city, String state, String location, Integer experience, String requirementName, Integer visibility, Integer openPositions, Integer closedPositions, Integer candidateCount, LocalDate publishedOn, String createdBy, Integer totalPositions, String requirementStatus, Long candidateId, String fullName, String mobileNo, String emailId, Integer totalExperience, String currentlyWorkingAs, String currentlyWorkingAt, String currentLocation, String preferredLocation, Double expectedCtcPa, Integer noticePeriod, Boolean currentlyServingNoticePeriod, String comments, String availability, Long candidateEmployeeId, String resourceType, String candidateRequirementName, String rrfLink, String status, Double offeredCTC, Integer candidateVisibility, String recordAuthor, String ipAddress, String vendor, LocalDate creationDate, String source, String subStatus, String additionalSkills) {
        this.rrfId = rrfId;
        this.customerName = customerName;
        this.primarySkills = primarySkills;
        this.hiringType = hiringType;
        this.jobLevel = jobLevel;
        this.minExp = minExp;
        this.maxExp = maxExp;
        this.budget = budget;
        this.secondarySkills = secondarySkills;
        this.employeeId = employeeId;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.priority = priority;
        this.modeOfWork = modeOfWork;
        this.title = title;
        this.ownerOfRequirement = ownerOfRequirement;
        this.city = city;
        this.state = state;
        this.location = location;
        this.experience = experience;
        this.requirementName = requirementName;
        this.visibility = visibility;
        this.openPositions = openPositions;
        this.closedPositions = closedPositions;
        this.candidateCount = candidateCount;
        this.publishedOn = publishedOn;
        this.createdBy = createdBy;
        this.totalPositions = totalPositions;
        this.requirementStatus = requirementStatus;
        this.candidateId = candidateId;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.emailId = emailId;
        this.totalExperience = totalExperience;
        this.currentlyWorkingAs = currentlyWorkingAs;
        this.currentlyWorkingAt = currentlyWorkingAt;
        this.currentLocation = currentLocation;
        this.preferredLocation = preferredLocation;
        this.expectedCtcPa = expectedCtcPa;
        this.noticePeriod = noticePeriod;
        this.currentlyServingNoticePeriod = currentlyServingNoticePeriod;
        this.comments = comments;
        this.availability = availability;
        this.candidateEmployeeId = candidateEmployeeId;
        this.resourceType = resourceType;
        this.candidateRequirementName = candidateRequirementName;
        this.rrfLink = rrfLink;
        this.status = status;
        this.offeredCTC = offeredCTC;
        this.candidateVisibility = candidateVisibility;
        this.recordAuthor = recordAuthor;
        this.ipAddress = ipAddress;
        this.vendor = vendor;
        this.creationDate = creationDate;
        this.source = source;
        this.subStatus = subStatus;
        this.additionalSkills = additionalSkills;
    }

    // Getters and setters (or use Lombok for boilerplate code)
}
