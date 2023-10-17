package com.inventoryservice.service;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.dto.OrderResponseDTO;

public interface InventoryService {
	
	public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest);
	public String updateInventory(InventoryRequestDTO inventoryRequest, Long productId);
	public InventoryResponseDTO getInventory(Long productId);
	public void checkInventory(OrderResponseDTO order);
	

}
