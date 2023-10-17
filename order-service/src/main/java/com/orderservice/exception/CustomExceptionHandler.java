package com.orderservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.orderservice.dto.ErrorResponseDTO;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleOtherExceptions(Exception ex) {
		return new ResponseEntity<>(ErrorResponseDTO.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
				.errorMessage(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
