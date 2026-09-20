package com.enter.inventory_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Value Object (VO) representing the incoming event from the Order Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String eventId;
    private Long orderId;
    private String productId;
    private Integer quantity;
}