package com.inventoryservice.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inventoryservice.configuration.FeignConfiguration;
import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.dto.UserResponseDTO;
import com.inventoryservice.entity.Inventory;
import com.inventoryservice.exception.NotAuthorizedUserException;
import com.inventoryservice.exception.ResourceNotFoundException;
import com.inventoryservice.repository.InventoryRepository;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.utility.Constants;
import com.inventoryservice.utility.ModelConverter;
import com.inventoryservice.utility.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private final FeignConfiguration feignConfiguration;
	private final ModelConverter converter;

	@Override
	public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest) {
		UserResponseDTO user = getUserDeatails(inventoryRequest.getUserId());
		if (user.getRole().equals(UserRole.USER)) {
			throw new NotAuthorizedUserException(Constants.UNAUTHORIZED_PREVILLEGE);
		}
		Inventory inventory = inventoryRepository.save(converter.convert(inventoryRequest, Inventory.class));
		return converter.convert(inventory, InventoryResponseDTO.class);
	}

	@Override
	public String updateInventory(InventoryRequestDTO inventoryRequest, Long productId) {
		UserResponseDTO user = getUserDeatails(inventoryRequest.getUserId());
		if (user.getRole().equals(UserRole.USER)) {
			throw new NotAuthorizedUserException(Constants.UNAUTHORIZED_PREVILLEGE);
		}
		Inventory inventory = inventoryRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
		inventory.setQuantity(inventoryRequest.getQuantity());
		inventory.setUpdatedAt(LocalDateTime.now());
		inventoryRepository.save(inventory);
		return Constants.SUCCESS;
	}

	public UserResponseDTO getUserDeatails(String email) {
		return feignConfiguration.getUserByEmail(email).getBody();
	}

	@Override
	public InventoryResponseDTO getInventory(Long productId) {
		Inventory inventory = inventoryRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND));
		return converter.convert(inventory, InventoryResponseDTO.class);
	}

}
