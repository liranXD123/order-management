package com.enter.order_service.dto;

import com.enter.order_service.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for outgoing order responses.
 * Returns the order details without exposing the database entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    
    private Long id;
    private String productId;
    private Integer quantity;
    private OrderStatus status;
}