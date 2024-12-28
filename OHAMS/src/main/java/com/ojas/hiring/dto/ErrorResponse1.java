package com.ojas.hiring.dto;

import lombok.Data;

@Data
public class ErrorResponse1 {

	int status;
	String message;
	ErrorResponse1(){
		
	}
	public ErrorResponse1(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
}
