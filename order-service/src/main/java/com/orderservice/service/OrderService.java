package com.orderservice.service;

import com.orderservice.dto.OrderRequestDTO;
import com.orderservice.dto.OrderResponseDTO;

public interface OrderService {
	
	public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest);
	public OrderResponseDTO updateOrderStatus(Long orderId, String status);

}
