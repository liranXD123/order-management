package com.enter.order_service.service;

import com.enter.order_service.dto.OrderRequestDto;
import com.enter.order_service.dto.OrderResponseDto;

/**
 * Service interface for handling order business logic.
 */
public interface OrderService {
    
    /**
     * Processes the creation of a new order.
     */
    OrderResponseDto createOrder(OrderRequestDto requestDto);

    /**
     * Retrieves an order by its ID.
     */
    OrderResponseDto getOrder(Long id);
}