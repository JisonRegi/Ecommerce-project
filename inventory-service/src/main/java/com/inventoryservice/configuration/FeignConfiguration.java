package com.inventoryservice.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.inventoryservice.dto.UserResponseDTO;


@Configuration
@FeignClient(name="USER-SERVICE")
public interface FeignConfiguration {
	
	@GetMapping("users/{email}")
	public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email);

}
