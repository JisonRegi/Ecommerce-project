package com.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long id;
	private String email;
	private List<AddressDTO> address;
}
