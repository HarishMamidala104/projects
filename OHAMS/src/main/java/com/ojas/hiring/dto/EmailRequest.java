package com.ojas.hiring.dto;

import lombok.Data;

@Data
public class EmailRequest {

	private String toEmail;
	private String ccEmail;
	private String subject;
	private String body;
	
}
