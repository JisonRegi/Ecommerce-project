package com.inventoryservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inventoryservice.dto.ErrorResponseDTO;

@ControllerAdvice
public class RestCustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleOtherException(Exception ex) {
		return new ResponseEntity<>(ErrorResponseDTO.builder().errorMessage(ex.getMessage())
				.errorCode("INTERNAL_SERVER_ERROR").status(500).timestamp(LocalDateTime.now()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponseDTO> handleCustomExceptions(CustomException ex) {
		return new ResponseEntity<>(ErrorResponseDTO.builder().errorMessage(ex.getMessage()).errorCode(ex.getErrorCode())
				.status(ex.getStatus()).timestamp(LocalDateTime.now()).build(), HttpStatus.valueOf(ex.getStatus()));
	}

}
