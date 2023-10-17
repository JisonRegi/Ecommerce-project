package com.userservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.userservice.dto.UserEventDTO;
import com.userservice.dto.UserRequestDTO;
import com.userservice.dto.UserResponseDTO;
import com.userservice.entity.User;
import com.userservice.exception.CustomException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import com.userservice.util.Constants;
import com.userservice.util.ModelCoverter;
import com.userservice.util.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final ModelCoverter convertor;
	private final UserRepository userRepository;
	private final KafkaTemplate<String, UserEventDTO> kafkaTemplate;

	@Override
	public UserResponseDTO getUserByEmail(String email) {
		User user = findUserByEmail(email);
		return convertor.convert(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO registerUser(UserRequestDTO userRequest) {
		if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
			throw new CustomException("User already exists", HttpStatus.CONFLICT.toString(),
					HttpStatus.CONFLICT.value());
		}
		userRequest.setRole(UserRole.USER);
		User user = userRepository.save(convertor.convert(userRequest, User.class));
		sendNotificationEvent(user.getEmail());
		return convertor.convert(user, UserResponseDTO.class);
	}

	@Override
	public UserResponseDTO loginUser(UserRequestDTO userRequest) {
		User user = findUserByEmail(userRequest.getEmail());
		if (!user.getPassword().equals(userRequest.getPassword())) {
			throw new CustomException("Invalid Credentials", HttpStatus.UNAUTHORIZED.toString(),
					HttpStatus.UNAUTHORIZED.value());
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
		return userRepository.findByEmail(email).orElseThrow(() -> new CustomException("User not found",
				HttpStatus.NOT_FOUND.toString(), HttpStatus.NOT_FOUND.value()));
	}

	private void sendNotificationEvent(String email) {
		UserEventDTO userEvent = new UserEventDTO();
		userEvent.setEmail(email);
		kafkaTemplate.send(Constants.USER_CREATED_EVENT, userEvent);
	}

}
