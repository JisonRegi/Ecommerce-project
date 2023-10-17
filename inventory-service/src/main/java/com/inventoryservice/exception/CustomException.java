package com.inventoryservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private int status;

	public CustomException(String errorMessage, String errorCode, int status) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.status = status;
	}

}
