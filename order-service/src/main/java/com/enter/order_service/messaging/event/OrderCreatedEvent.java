package com.enter.order_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Value Object (VO) representing the event published when an order is created.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    @Builder.Default
    private String eventId = UUID.randomUUID().toString();
    private Long orderId;
    private String productId;
    private Integer quantity;
}