package com.ojas.hiring.dto;

import com.ojas.hiring.entity.Interviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CandidateDto {
    private long cid;
    private String fullName;
    private String mobileNo;

    private String emailId;
    private double totalExperience;
    private String currentlyWorkingAs;
    private String currentlyWorkingAt;
    private String currentLocation;
    private String preferredLocation;
    private double ctcPA;
    private double expectedCtcPa;
    private double noticePeriod;
    private String currentlyServingNoticePeriod;
    private String comments;
    private String availability;
    private long employeeId;
    private String resourceType;
    private String requirementName;


    private Map<String, Double> skillSet;


    private long rrfLink;


    private String status;

    private double offered_CTC;

    private int visibility;


    private String recordAuthor;


    private String ipAddress;




//    private List<InterviewDto> interviews;

    private String vendor;


    private Date creationDate;


    private String source;


    private String additionalSkills;


    private String subStatus;

    private String primarySkills;

    private String hiringType;

    private String tagExecutive;

    private String customerName;

    private String priority;

    private String ownerOfRequirement;

    private int totalPositions;

    private int openPositions;

    private int closedPositions;

    private int candidateCount;

    private String publishedOn;
    
    private List<Interviews> interviews;
    
   

}
