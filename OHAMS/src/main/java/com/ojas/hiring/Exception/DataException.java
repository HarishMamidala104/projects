package com.ojas.hiring.Exception;

public class DataException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final String error;
	public DataException(String error) {
		this.error = error;
	}
}
