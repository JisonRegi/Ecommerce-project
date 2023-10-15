package com.inventoryservice.dto;


import com.inventoryservice.utility.UserRole;

import lombok.Data;

@Data
public class UserResponseDTO {
	private String email;
	private UserRole role;
}
