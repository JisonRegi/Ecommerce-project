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

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "USER-SERVICE", configuration = CustomErrorDecoder.class)
public interface UserService {

	@GetMapping("users/{email}")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

	default public ResponseEntity<UserResponseDTO> fallback(Throwable ex) {
		throw new CustomException("User service is not available", HttpStatus.SERVICE_UNAVAILABLE.toString(), 503);
	}

}
