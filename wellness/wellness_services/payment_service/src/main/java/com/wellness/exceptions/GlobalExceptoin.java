package com.wellness.exceptions;

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

import com.wellness.payload.ResponseDTO;


@RestControllerAdvice
public class GlobalExceptoin extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
			
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PaymentIdNotFoundException.class)
	protected ResponseEntity<Map<String, Object>> paymentIdNotFoundException(PaymentIdNotFoundException exception){
		
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
}
