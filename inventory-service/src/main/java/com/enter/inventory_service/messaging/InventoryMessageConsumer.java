package com.enter.inventory_service.messaging;

import com.enter.inventory_service.config.RabbitMQConfig;
import com.enter.inventory_service.messaging.event.InventoryResponseEvent;
import com.enter.inventory_service.messaging.event.OrderCreatedEvent;
import com.enter.inventory_service.service.InventoryService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer responsible for listening to new orders and triggering inventory reservation.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryMessageConsumer {

    private final InventoryService inventoryService;
    private final InventoryMessagePublisher inventoryMessagePublisher;
    private final Gson gson;

    /**
     * Listens to the order created queue.
     * Deserializes the message, checks inventory, and publishes the response.
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void receiveOrderCreatedEvent(String jsonMessage) {
        log.info("Received message from order created queue: {}", jsonMessage);
        
        try {
            // 1. Deserialize the incoming JSON message
            OrderCreatedEvent orderEvent = gson.fromJson(jsonMessage, OrderCreatedEvent.class);
            
            // 2. Execute business logic: Check and reserve inventory
            boolean isAvailable = inventoryService.checkAndReserveInventory(
                    orderEvent.getProductId(), 
                    orderEvent.getQuantity()
            );
            
            // 3. Prepare the response event
            InventoryResponseEvent responseEvent = InventoryResponseEvent.builder()
                    .orderId(orderEvent.getOrderId())
                    .inventoryAvailable(isAvailable)
                    .build();
                    
            // 4. Send the response back through the Publisher
            inventoryMessagePublisher.publishInventoryResponse(responseEvent);
            
        } catch (Exception e) {
            log.error("Failed to process order created message: {}", e.getMessage(), e);
        }
    }
}