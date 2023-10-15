package com.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping("{productId}")
	public ResponseEntity<InventoryResponseDTO> getInventory(@PathVariable Long productId){
		return new ResponseEntity<InventoryResponseDTO>(inventoryService.getInventory(productId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InventoryResponseDTO> createInventory(@RequestBody InventoryRequestDTO inventoryRequest){
		return new ResponseEntity<InventoryResponseDTO>(inventoryService.createInventory(inventoryRequest),HttpStatus.CREATED);
	}
	
	@PutMapping("{productId}/update")
	public ResponseEntity<String> updateInventory(@PathVariable Long productId, @RequestBody InventoryRequestDTO inventoryRequest){
		return new ResponseEntity<String>(inventoryService.updateInventory(inventoryRequest, productId),HttpStatus.OK);
	}

}
