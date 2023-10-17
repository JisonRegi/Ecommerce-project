package com.inventoryservice.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.inventoryservice.dto.OrderResponseDTO;
import com.inventoryservice.service.InventoryService;
import com.inventoryservice.utility.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class OrderEventListner {

	private final InventoryService inventoryService;
	
	@KafkaListener(topics = Constants.ORDER_CREATED, groupId = Constants.ORDERS_GROUP)
	public void handleOrderCreatedEvent(OrderResponseDTO order) {
		log.info("checking inventory");
		inventoryService.checkInventory(order);
	}
}
