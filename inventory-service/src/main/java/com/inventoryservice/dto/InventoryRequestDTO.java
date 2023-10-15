package com.inventoryservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InventoryRequestDTO {
	private String productName;
	private String description;
	private BigDecimal price;
	private int quantity;
	private String userId;
}
