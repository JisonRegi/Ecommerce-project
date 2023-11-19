package com.inventoryservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inventoryservice.dto.UserResponseDTO;
import com.inventoryservice.exception.CustomException;
import com.inventoryservice.external.decoder.CustomErrorDecoder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "user-service",  url = "user-service:8001")
public interface UserService {

	@CircuitBreaker(name = "external", fallbackMethod = "fallback")
	@GetMapping("users/{email}")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

	default public ResponseEntity<UserResponseDTO> fallback(Exception ex) {
		if (ex instanceof CustomException custom) {
			throw new CustomException(ex.getMessage(), custom.getErrorCode(), custom.getStatus());
		}
		throw new CustomException("User service is not available", HttpStatus.SERVICE_UNAVAILABLE.toString(), 503);
	}

}
