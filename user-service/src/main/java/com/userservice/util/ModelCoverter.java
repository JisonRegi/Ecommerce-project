package com.userservice.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelCoverter {

	private final ModelMapper modelMapper;

	public <T> T convert(Object source, Class<T> clazz) {
		return modelMapper.map(source, clazz);
	}

}
