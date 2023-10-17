package com.userservice.dto;

import com.userservice.util.UserStatus;

import lombok.Data;

@Data
public class UserEventDTO {
	private String email;
	private UserStatus status;
}
