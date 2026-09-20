package com.enter.inventory_service.messaging;

import com.enter.inventory_service.config.RabbitMQConfig;
import com.enter.inventory_service.messaging.event.InventoryResponseEvent;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Publisher responsible for sending inventory responses back to the Order Service via RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryMessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    /**
     * Publishes an InventoryResponseEvent to the exchange.
     * The routing key changes dynamically based on whether inventory was reserved or rejected.
     */
    public void publishInventoryResponse(InventoryResponseEvent event) {
        String jsonMessage = gson.toJson(event);
        
        // Define dynamic routing key based on the business outcome
        String routingKey = event.isInventoryAvailable() ? "inventory.reserved" : "inventory.rejected";
        
        log.info("Publishing InventoryResponseEvent for orderId: {} with routingKey: {}", 
                 event.getOrderId(), routingKey);
                 
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME, 
                routingKey, 
                jsonMessage
        );
    }
}