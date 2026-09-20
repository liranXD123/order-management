package com.enter.inventory_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Value Object (VO) representing the outgoing response back to the Order Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseEvent {
    @Builder.Default
    private String eventId = UUID.randomUUID().toString();
    private Long orderId;
    private boolean inventoryAvailable;
}