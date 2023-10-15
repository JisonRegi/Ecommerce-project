package com.inventoryservice.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
	private String errorMessage;
	private String errorDetails;
	private String errorCode;
	private LocalDateTime timestamp;
}
