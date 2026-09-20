package com.enter.inventory_service.config;

import com.google.gson.Gson;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ in the Inventory Service.
 */
@Configuration
public class RabbitMQConfig {

    // The shared exchange used by both services
    public static final String EXCHANGE_NAME = "order.exchange";
    
    // The queue this service will listen to for new orders
    public static final String ORDER_CREATED_QUEUE = "order.created.queue";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange orderExchange) {
        // We bind the queue to listen to the exact routing key the Order Service uses to publish
        return BindingBuilder.bind(orderCreatedQueue).to(orderExchange).with("order.created");
    }

    /**
     * Creates a Gson bean to be used for message serialization and deserialization,
     * maintaining consistency with the Order Service.
     */
    @Bean
    public Gson gson() {
        return new Gson();
    }
}