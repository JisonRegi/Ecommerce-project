package com.orderservice.events;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.service.OrderService;
import com.orderservice.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OrderEventsListner {

	private final OrderService orderService;

	@KafkaListener(topics = Constants.ORDER_CANCELLED, groupId = Constants.ORDERS_GROUP)
	public void handleOrderCancelledEvent(OrderResponseDTO orderResponse) {
		log.info("triggered order cancellation for "+ orderResponse.getOrderId());
		orderService.updateOrderStatus(orderResponse.getOrderId(), orderResponse.getOrderStatus().name());
	}
}
