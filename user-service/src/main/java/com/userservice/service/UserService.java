package com.userservice.service;

import com.userservice.dto.UserRequestDTO;
import com.userservice.dto.UserResponseDTO;

public interface UserService {
	
	public UserResponseDTO registerUser(UserRequestDTO userRequest);
	public UserResponseDTO loginUser(UserRequestDTO userRequest);
	public UserResponseDTO getUserByEmail(String email);
	public String resetPassword(UserRequestDTO userRequest);
	public String deleteUser(String email);

}
