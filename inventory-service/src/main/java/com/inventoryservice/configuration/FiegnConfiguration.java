package com.inventoryservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inventoryservice.external.decoder.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class FiegnConfiguration {

	@Bean
	ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
	
}
