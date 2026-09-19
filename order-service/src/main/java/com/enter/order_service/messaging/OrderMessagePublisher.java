package com.enter.order_service.messaging;

import com.enter.order_service.config.RabbitMQConfig;
import com.enter.order_service.messaging.event.OrderCreatedEvent;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Publisher responsible for sending order-related events to RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    /**
     * Publishes an OrderCreatedEvent to the exchange.
     * Uses Gson to serialize the object into a JSON string before sending.
     */
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        String jsonMessage = gson.toJson(event);
        
        log.info("Publishing OrderCreatedEvent for orderId: {} with eventId: {}", 
                 event.getOrderId(), event.getEventId());
                 
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME, 
                RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, 
                jsonMessage
        );
    }
}