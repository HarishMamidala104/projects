package com.wellness.subscription.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptoin extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);

		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SubscriptionDetailsNotFound.class)
	protected ResponseEntity<Map<String, Object>> invalidPasswordException(SubscriptionDetailsNotFound exception) {

		Map<String, Object> response = new HashMap<>();
		response.put("message", exception.getMessage());
		response.put("status", HttpStatus.NOT_FOUND);
		response.put("timestamp", new Date());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(SubscriptionNotFound.class)
	protected ResponseEntity<Map<String, Object>> invalidPasswordException(SubscriptionNotFound exception) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", exception.getMessage());
		response.put("status", HttpStatus.NOT_FOUND);
		response.put("timestamp", new Date());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}
}