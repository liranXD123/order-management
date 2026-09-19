package com.enter.order_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Value Object (VO) representing the response from the Inventory Service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseEvent {
    private String eventId;
    private Long orderId;
    private boolean inventoryAvailable; // true if CONFIRMED, false if REJECTED
}