package com.orderservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {
	private String userEmail;
	private List<OrderItemDTO> items;
}
