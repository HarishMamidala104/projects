package com.ojas.hiring.Exception;

public class RestBaseException extends RuntimeException {

	private static final long serialVersionUID = -2012565415025637398L;
	private String code;
	private String reason;
	private String message;
	private String[] args;
	private Throwable cause;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RestBaseException() {
	}

	public RestBaseException(String reason, String message) {
		super(message);
		this.reason = reason;
		this.message = message;
	}

	public RestBaseException(String code, String reason, String message) {
		super(message);
		this.code = code;
		this.reason = reason;
		this.message = message;
	}

	public RestBaseException(String code, String reason, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.reason = reason;
		this.message = message;
		this.cause = cause;
	}

	public RestBaseException(String code, String reason, String message, Throwable cause, String... args) {
		super(message, cause);
		this.code = code;
		this.reason = reason;
		this.message = message;
		this.cause = cause;
		this.args = args;
	}

	public RestBaseException(String code, String reason, String message, String... args) {
		super();
		this.code = code;
		this.reason = reason;
		this.message = message;
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public synchronized Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		synchronized (this) {
			this.cause = cause;
		}
	}
}
