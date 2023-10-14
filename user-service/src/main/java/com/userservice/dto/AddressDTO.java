package com.userservice.dto;

import com.userservice.util.AddressType;

import lombok.Data;

@Data
public class AddressDTO {
	
	private AddressType addressType;
	private String city;
	private String pinCode;
}
