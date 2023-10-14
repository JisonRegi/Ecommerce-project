package com.userservice.entity;

import com.userservice.util.AddressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="address")
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private Long addressId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="address_type")
	private AddressType addressType;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pin")
	private String pinCode;
}
