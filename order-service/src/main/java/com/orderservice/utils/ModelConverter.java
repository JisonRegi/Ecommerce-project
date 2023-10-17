package com.orderservice.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelConverter {

	private final ModelMapper modelMapper;

	public <T> T convert(Object source, Class<T> clazz) {
		modelMapper.getConfiguration().setFieldMatchingEnabled(true)
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(source, clazz);
	}

}
