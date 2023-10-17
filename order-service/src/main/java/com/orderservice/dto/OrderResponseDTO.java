package com.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.orderservice.utils.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDTO {
	private Long orderId;
	private LocalDateTime orderAt;
	private String userEmail;
	private BigDecimal totalAmount;
	private OrderStatus orderStatus;
	private List<OrderItemDTO> items;
}
