package com.inventoryservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Long productId;
	private int quantity;
	private BigDecimal price;
}
