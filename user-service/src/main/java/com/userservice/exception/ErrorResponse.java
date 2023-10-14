package com.userservice.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
	private String errorMessage;
	private String errorCode;
	private LocalDateTime timestamp;
}
