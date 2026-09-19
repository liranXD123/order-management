package com.enter.order_service.messaging;

import com.enter.order_service.config.RabbitMQConfig;
import com.enter.order_service.messaging.event.InventoryResponseEvent;
import com.enter.order_service.service.OrderService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer responsible for listening to inventory responses from RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMessageConsumer {

    private final OrderService orderService;
    private final Gson gson;

    /**
     * Listens to the inventory response queue.
     * Deserializes the JSON message using Gson and updates the order status.
     * 
     * @param jsonMessage the raw JSON string received from the queue
     */
    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESPONSE_QUEUE)
    public void receiveInventoryResponse(String jsonMessage) {
        log.info("Received message from inventory response queue: {}", jsonMessage);
        
        try {
            // Deserialize the JSON string back into a Java object using Gson
            InventoryResponseEvent responseEvent = gson.fromJson(jsonMessage, InventoryResponseEvent.class);
            
            // Update the order status via the Service layer (fulfilling business flow steps 8 & 10)
            orderService.updateOrderStatus(responseEvent.getOrderId(), responseEvent.isInventoryAvailable());
            
        } catch (Exception e) {
            // Catching exceptions ensures the message doesn't infinitely retry if the JSON is malformed
            log.error("Failed to process inventory response message: {}", e.getMessage(), e);
        }
    }
}