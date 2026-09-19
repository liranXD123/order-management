package com.enter.order_service.mapper;

import com.enter.order_service.dto.OrderRequestDto;
import com.enter.order_service.dto.OrderResponseDto;
import com.enter.order_service.entity.Order;
import com.enter.order_service.entity.OrderStatus;
import org.springframework.stereotype.Component;

/**
 * Mapper component to convert between Entities and DTOs.
 * Enforces the architectural rule of not exposing Entities directly.
 */
@Component
public class OrderMapper {

    /**
     * Maps an incoming request DTO to an Order Entity.
     * Sets the initial status to PENDING_INVENTORY as required by the business flow.
     *
     * @param requestDto the incoming request
     * @return a new Order entity ready to be saved
     */
    public Order toEntity(OrderRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        return Order.builder()
                .productId(requestDto.getProductId())
                .quantity(requestDto.getQuantity())
                // Requirement Flow Step 3: Order status becomes PENDING_INVENTORY
                .status(OrderStatus.PENDING_INVENTORY) 
                .build();
    }

    /**
     * Maps an Order Entity to an outgoing response DTO.
     *
     * @param order the persisted entity
     * @return the response DTO
     */
    public OrderResponseDto toResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }
}