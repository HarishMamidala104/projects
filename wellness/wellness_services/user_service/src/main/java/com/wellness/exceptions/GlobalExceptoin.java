package com.wellness.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
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


import com.wellness.payloads.ResponseDTO;
import jakarta.validation.ConstraintViolationException;

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
	
	@ExceptionHandler(InvalidEmailException.class)
	protected ResponseEntity<Map<String, Object>> invalidEmailException(InvalidEmailException exception){
		
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	protected ResponseEntity<Map<String, Object>> invalidPasswordException(InvalidPasswordException exception){
		
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Map<String, Object>> userNotFound(UserNotFoundException exception){
		
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(PasswordMisMatchException.class)
	protected ResponseEntity<Map<String, Object>> passwordMisMatch(PasswordMisMatchException exception){
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		
        // Check if the exception is related to a unique constraint violation
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintEx = (ConstraintViolationException) ex.getCause();
            String errorMessage = constraintEx.getMessage(); // Error message with details
            // Handle the unique constraint violation error here
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
        // Handle other types of exceptions if needed
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("An error occurred");
    }
	
	@ExceptionHandler(ProfilePictureNotFoundException.class)
	protected ResponseEntity<Map<String, Object>> profilePictureNotFound(ProfilePictureNotFoundException exception){
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(InvalidImageFileException.class)
	protected ResponseEntity<Map<String, Object>> invalidImageFileException(InvalidImageFileException exception){
		ResponseDTO payload = ResponseDTO.builder().messgae(exception.getMessage()).status(HttpStatus.NOT_FOUND).timeStamp(new Date()).build();
		Map<String, Object> response = new HashMap<>();
		response.put("message", payload.getMessgae());
		response.put("status", payload.getStatus());
		response.put("timestamp", payload.getTimeStamp());
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		
	}
	
}
