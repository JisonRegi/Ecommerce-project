package com.inventoryservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.inventoryservice.utility.OrderStatus;


import lombok.Data;

@Data
public class OrderResponseDTO {
	private Long orderId;
	private LocalDateTime orderAt;
	private String userId;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	private List<OrderItemDTO> items;
	
}
