package com.orderservice.service.impl;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orderservice.dto.OrderRequestDTO;
import com.orderservice.dto.OrderResponseDTO;
import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import com.orderservice.utils.Constants;
import com.orderservice.utils.ModelConverter;
import com.orderservice.utils.OrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ModelConverter converter;
	private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

	@Override
	public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
		Order order = converter.convert(orderRequest, Order.class);
		order.setOrderAt(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.CREATED);
		OrderResponseDTO orderResponse = converter.convert(orderRepository.save(order), OrderResponseDTO.class);
		log.info("order created with id: " + orderResponse.getOrderId() + "user: " + orderResponse.getUserEmail());
		kafkaTemplate.send(Constants.ORDER_CREATED, orderResponse);
		log.info("order created event send successfully for order: " + orderResponse.getOrderId());
		return orderResponse;
	}

	@Override
	public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
		Order order = orderRepository.findById(orderId).get();
		order.setOrderStatus(OrderStatus.valueOf(status));
		return converter.convert(orderRepository.save(order), OrderResponseDTO.class);
	}

}
