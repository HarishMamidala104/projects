package com.ojas.hiring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RestBaseException{

	private static final long serialVersionUID = -3452834794802654634L;

	public BadRequestException(String code, String reason, String message) {
		super(code,reason,message);
	}
	
	public BadRequestException(String code, String reason, String message,String ...args) {
		super(code,reason,message,args);
	}
	
	public BadRequestException(String code, String reason, String message,Throwable exception) {
		super(code,reason,message,exception);
	}
	
	public BadRequestException(String code, String reason, String message,Throwable exception,String ...args) {
		super(code,reason,message,exception,args);
	}
}
