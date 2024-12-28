package com.ojas.hiring.exceptions;

import javax.mail.Message;

public class RequirementOwnerInvalid extends RuntimeException {

	public RequirementOwnerInvalid(String message) {
	
		super(message);
	}
}
