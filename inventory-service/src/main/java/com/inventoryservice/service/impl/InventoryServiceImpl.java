package com.inventoryservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inventoryservice.dto.InventoryRequestDTO;
import com.inventoryservice.dto.InventoryResponseDTO;
import com.inventoryservice.dto.OrderItemDTO;
import com.inventoryservice.dto.OrderResponseDTO;
import com.inventoryservice.dto.UserResponseDTO;
import com.inventoryservice.entity.Inventory;
import com.inventoryservice.exception.CustomException;
import com.inventoryservice.external.client.UserService;
import com.inventoryservice.repository.InventoryRepository;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.utility.Constants;
import com.inventoryservice.utility.ModelConverter;
import com.inventoryservice.utility.OrderStatus;
import com.inventoryservice.utility.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private final UserService feignConfiguration;
	private final ModelConverter converter;
	private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

	@Override
	public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest) {
		UserResponseDTO user = getUserDeatails(inventoryRequest.getUserId());
		if (user.getRole().equals(UserRole.USER)) {
			throw new CustomException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(),
					HttpStatus.UNAUTHORIZED.value());
		}
		Inventory inventory = inventoryRepository.save(converter.convert(inventoryRequest, Inventory.class));
		return converter.convert(inventory, InventoryResponseDTO.class);
	}

	@Override
	public String updateInventory(InventoryRequestDTO inventoryRequest, Long productId) {
		UserResponseDTO user = getUserDeatails(inventoryRequest.getUserId());
		if (user.getRole().equals(UserRole.USER)) {
			throw new CustomException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(),
					HttpStatus.UNAUTHORIZED.value());
		}
		Inventory inventory = inventoryRepository.findById(productId)
				.orElseThrow(() -> new CustomException("Resource not found", HttpStatus.NOT_FOUND.toString(),
						HttpStatus.NOT_FOUND.value()));
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
				.orElseThrow(() -> new CustomException("Resource not found", HttpStatus.NOT_FOUND.toString(),
						HttpStatus.NOT_FOUND.value()));
		return converter.convert(inventory, InventoryResponseDTO.class);
	}

	@Override
	public void checkInventory(OrderResponseDTO order) {
		List<Inventory> inventoryListUpdated = new ArrayList<>();
		List<OrderItemDTO> items = order.getItems();
		List<Long> productIds = items.stream().map(OrderItemDTO::getProductId).collect(Collectors.toList());
		Map<Long, Inventory> inventoryMap = inventoryRepository.findByProductIdIn(productIds).stream()
				.collect(Collectors.toMap(Inventory::getProductId, inv -> inv));
		for (OrderItemDTO item : items) {
			int quantityRequired = item.getQuantity();
			long productId = item.getProductId();
			if (inventoryMap.containsKey(item.getProductId())) {
				Inventory inventory = inventoryMap.get(productId);
				int quantityAvailable = inventory.getQuantity();
				if (quantityAvailable < quantityRequired) {
					callCancelOrderEvent(order);
					log.info("Product: " + productId + " out of stock. Trigerred cancel order event");
					return;
					// throw new OutOfStockException("Product: " + productId + " out of stock");
				} else {
					inventory.setQuantity(quantityAvailable - quantityRequired);
					inventoryListUpdated.add(inventory);
				}
			} else {
				callCancelOrderEvent(order);
				log.info("Product: " + productId + " not found. Trigerred cancel order event");
				return;
				// throw new ResourceNotFoundException("Product: " + productId + " not found");
			}
		}
		inventoryRepository.saveAll(inventoryListUpdated);
		log.info("Order processed for payment");
	}

	private void callCancelOrderEvent(OrderResponseDTO order) {
		order.setOrderStatus(OrderStatus.CANCELLED);
		kafkaTemplate.send(Constants.ORDER_CANCELLED, order);
	}

}
