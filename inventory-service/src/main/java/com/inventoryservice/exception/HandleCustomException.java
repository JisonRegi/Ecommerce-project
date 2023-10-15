package com.inventoryservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleCustomException extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(WebRequest wq, Exception ex){
		ErrorResponse errorResponse= new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setErrorDetails(ex.getLocalizedMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleOtherException(WebRequest wq, Exception ex){
		ErrorResponse errorResponse= new ErrorResponse();
		errorResponse.setErrorMessage(ex.getMessage());
		errorResponse.setErrorDetails(ex.getLocalizedMessage());
		errorResponse.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
