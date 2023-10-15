package com.userservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userservice.util.UserRole;

import lombok.Data;

@Data
public class UserRequestDTO {	
	private String email;
	private String password;
	private List<AddressDTO> address;
	
	@JsonIgnore
	private UserRole role;
}
