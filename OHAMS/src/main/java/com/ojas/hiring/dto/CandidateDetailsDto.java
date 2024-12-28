package com.ojas.hiring.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CandidateDetailsDto {

	  private String fullName;

	    private String emailId;

	    private String subStatus;
	    private String customerName;
	    private String creationDate;  // This stores the raw timestamp

	  
	    // Other getters and setters...
	}