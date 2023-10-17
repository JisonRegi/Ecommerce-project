package com.inventoryservice.external.decoder;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inventoryservice.dto.ErrorResponseDTO;
import com.inventoryservice.exception.CustomException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	public Exception decode(String methodKey, Response response) {
		try (InputStream inputStream = response.body().asInputStream()) {
			ErrorResponseDTO errorResponse = objectMapper.readValue(inputStream, ErrorResponseDTO.class);
			return new CustomException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(),
					response.status());
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}

}
