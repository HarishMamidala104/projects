package com.ojas.hiring.Exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ojas.hiring.exceptions.BaseException;
import com.ojas.hiring.exceptions.LocationNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", "Bad Request");
		body.put("code", ex.getCode());
		body.put("reason", ex.getReason());
		body.put("message", ex.getMessage());
		body.put("timestamp", System.currentTimeMillis());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<?> handleInternalServerException(InternalServerException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("error", "Internal Server Error");
		body.put("code", ex.getCode());
		body.put("reason", ex.getReason());
		body.put("message", ex.getMessage());
		body.put("timestamp", System.currentTimeMillis());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> handleBaseException(BaseException ex) {
		// Customize the response as needed
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("code", ex.getCode());
		responseBody.put("message", ex.getExceptionMsg());
		responseBody.put("data", ex.getResponseData());

		return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<Object> handleLocationNotFoundExceptionn(LocationNotFoundException ex) {
		// Customize the response as needed
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("code", HttpStatus.NOT_FOUND.value());
		responseBody.put("data", ex.getMessage());

		return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("code", HttpStatus.BAD_REQUEST.value());
		responseBody.put("data", getErrorMessages(ex.getBindingResult().getFieldErrors()));
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	private String getErrorMessages(List<FieldError> fieldErrors) {
		StringBuilder errorMessage = new StringBuilder("Validation failed: ");
		for (FieldError error : fieldErrors) {
			errorMessage.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("; ");
		}
		return errorMessage.toString();
	}

}
