package com.ojas.hiring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BaseController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NoRecordFoundException.class })
	public ResponseEntity<ErrorMessage> handleNoRecordFoundException(NoRecordFoundException exception) {

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

		return new ResponseEntity<ErrorMessage>(errorMessage, errorMessage.getStatus());
	}

	@ExceptionHandler({ RrfIdNotFound.class })
	public ResponseEntity<ErrorMessage> handleRrfIdNotFoundException(RrfIdNotFound exception) {

		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());

		return new ResponseEntity<ErrorMessage>(errorMessage, errorMessage.getStatus());
	}

	@ExceptionHandler({ UserNotFound.class })
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFound exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}

	@ExceptionHandler({ PasswordMismatchException.class })
	public ResponseEntity<ErrorMessage> passwordMismatchException(PasswordMismatchException exception) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<ErrorMessage>(message, message.getStatus());

	}
	
	@ExceptionHandler({ CandidateChecking.class })
	public ResponseEntity<ErrorMessage> handleCandidateChecking(CandidateChecking exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}

	@ExceptionHandler({ InvalidUserId.class })
	public ResponseEntity<ErrorMessage> userIdExistedException(InvalidUserId exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ EmployeeIdChecking.class })
	public ResponseEntity<ErrorMessage> EmployeeIdCheckingException(EmployeeIdChecking exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ EmailChecking.class })
	public ResponseEntity<ErrorMessage> EmailCheckingException(EmailChecking exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ CustomerInvalid.class })
	public ResponseEntity<ErrorMessage> CustomerInvalidException(CustomerInvalid exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ InvalidVendor.class })
	public ResponseEntity<ErrorMessage> InvalidVendorException(InvalidVendor exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ InvalidInterviewer.class })
	public ResponseEntity<ErrorMessage> InvalidInterviewerException(InvalidInterviewer exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}

	@ExceptionHandler({ InvalidTechnology.class })
	public ResponseEntity<ErrorMessage> InvalidTechnologyException(InvalidTechnology exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
	
	@ExceptionHandler({ RequirementOwnerInvalid.class })
	public ResponseEntity<ErrorMessage> RequirementOwnerInvalidException(RequirementOwnerInvalid exception) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

	}
}

