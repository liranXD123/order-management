package com.enter.order_service.config;

import com.google.gson.Gson;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ.
 * Defines Exchanges, Queues, and Routing Keys.
 */
@Configuration
public class RabbitMQConfig {

    // Exchange name shared across services
    public static final String EXCHANGE_NAME = "order.exchange";
    
    // Routing keys for events
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";
    
    // The queue this service will listen to for inventory responses
    public static final String INVENTORY_RESPONSE_QUEUE = "inventory.response.queue";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue inventoryResponseQueue() {
        return new Queue(INVENTORY_RESPONSE_QUEUE, true);
    }

    @Bean
    public Binding inventoryResponseBinding(Queue inventoryResponseQueue, TopicExchange orderExchange) {
        // We bind the queue to listen to any routing key starting with "inventory." (e.g., inventory.reserved)
        return BindingBuilder.bind(inventoryResponseQueue).to(orderExchange).with("inventory.#");
    }

    /**
     * Creates a Gson bean to be used for message serialization and deserialization, 
     * fulfilling the specific business requirement.
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }
}