package com.ojas.hiring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetAllCandidatesDtoInfo {

	    @JsonProperty("id")
	    private Long rrfId;

	    @JsonProperty("customer_name")
	    private String customerName;

	    @JsonProperty("primary_skills")
	    private String primarySkills;

	    @JsonProperty("owner_of_requirement")
	    private String ownerOfRequirement;

	    @JsonProperty("requirement_Name")
	    private String requirementName;

	    @JsonProperty("open_positions")
	    private int openPositions;

	    @JsonProperty("closed_positions")
	    private int closedPositions;

	    @JsonProperty("published_on")
	    private String publishedOn;

	    @JsonProperty("total_positions")
	    private int totalPositions;
	    @JsonProperty("candidate_count")
	    private int candidateCount;
	}
