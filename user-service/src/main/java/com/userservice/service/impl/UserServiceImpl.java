package com.userservice.service.impl;

import org.springframework.stereotype.Service;

import com.userservice.dto.UserRequestDTO;
import com.userservice.dto.UserResponseDTO;
import com.userservice.entity.User;
import com.userservice.exception.InvalidCredentials;
import com.userservice.exception.ResourceExistsException;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import com.userservice.util.Constants;
import com.userservice.util.ModelCoverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final ModelCoverter convertor;
	private final UserRepository userRepository;

	@Override
	public UserResponseDTO getUserByEmail(String email) {
		User user = findUserByEmail(email);
		return convertor.convert(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequest) {
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
			throw new ResourceExistsException(Constants.USER_EXISTS);
		}
		User user = userRepository.save(convertor.convert(userRequest, User.class));
		return convertor.convert(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO loginUser(UserRequestDTO userRequest) {
		User user = findUserByEmail(userRequest.getEmail());
		if (!user.getPassword().equals(userRequest.getPassword())) {
			throw new InvalidCredentials(Constants.INVALID_CREDENTIALS);
		}
		return convertor.convert(user, UserResponseDTO.class);
	}

	@Override
	public String resetPassword(UserRequestDTO userRequest) {
		User user = findUserByEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		userRepository.save(user);
		return Constants.SUCCESS;
	}

	@Override
	public String deleteUser(String email) {
		User user = findUserByEmail(email);
		userRepository.delete(user);
		return Constants.SUCCESS;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
	}

}
