package com.userservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserRequestDTO {	
	private String email;
	private String password;
	private List<AddressDTO> address;
}
