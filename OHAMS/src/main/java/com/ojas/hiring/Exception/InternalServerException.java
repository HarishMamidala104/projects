package com.ojas.hiring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RestBaseException{

	private static final long serialVersionUID = -878010188767202613L;

	public InternalServerException(String code, String reason, String message) {
		super(code,reason,message);
	}
	
	public InternalServerException(String code, String reason, String message,String ...args) {
		super(code,reason,message,args);
	}
	
	public InternalServerException(String code, String reason, String message,Throwable exception) {
		super(code,reason,message,exception);
	}
	public InternalServerException(String code, String reason, String message,Throwable exception,String ...args) {
		super(code,reason,message,exception,args);
	}
}

