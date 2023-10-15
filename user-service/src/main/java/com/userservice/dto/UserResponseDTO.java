package com.userservice.dto;

import java.util.List;

import com.userservice.util.UserRole;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long id;
	private String email;
	private UserRole role;
	private List<AddressDTO> address;
}
