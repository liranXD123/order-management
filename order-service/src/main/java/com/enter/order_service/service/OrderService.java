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

    /**
     * Updates the status of an existing order based on inventory availability.
     * This method is called by the RabbitMQ consumer.
     * 
     * @param orderId the ID of the order to update
     * @param inventoryAvailable true if inventory was reserved, false if rejected
     */
    void updateOrderStatus(Long orderId, boolean inventoryAvailable);
}