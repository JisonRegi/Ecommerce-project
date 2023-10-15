package com.inventoryservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventoryResponseDTO {
	private Long productId;
	private String productName;
	private String description;
	private BigDecimal price;
	private int quantity;
	private LocalDateTime updatedAt;
	
}
