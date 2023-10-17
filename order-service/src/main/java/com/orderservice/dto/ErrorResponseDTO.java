package com.orderservice.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
	private String errorMessage;
	private String errorCode;
	private int status;
	private LocalDateTime timestamp;
}
