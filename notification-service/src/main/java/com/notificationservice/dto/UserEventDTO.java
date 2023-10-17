package com.notificationservice.dto;


import com.notificationservice.util.UserStatus;

import lombok.Data;

@Data
public class UserEventDTO {
	private String email;
	private UserStatus status;
}
