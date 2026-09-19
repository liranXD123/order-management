package com.enter.order_service.service;

import com.enter.order_service.dal.OrderDal;
import com.enter.order_service.dto.OrderRequestDto;
import com.enter.order_service.dto.OrderResponseDto;
import com.enter.order_service.entity.Order;
import com.enter.order_service.mapper.OrderMapper;
import com.enter.order_service.messaging.OrderMessagePublisher;
import com.enter.order_service.messaging.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderDal orderDal;
    private final OrderMapper orderMapper;
    private final OrderMessagePublisher orderMessagePublisher;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        log.info("Initiating order creation for productId: {}", requestDto.getProductId());
        
        // 1. Map DTO to Entity (Initial status is set in Mapper)
        Order order = orderMapper.toEntity(requestDto);
        
        // 2. Persist the order via DAL
        Order savedOrder = orderDal.save(order);
        
        log.info("Order successfully persisted with ID: {}", savedOrder.getId());
        
        // 3. Map Entity back to DTO for the response
        // 4. Publish OrderCreated event through RabbitMQ
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .productId(savedOrder.getProductId())
                .quantity(savedOrder.getQuantity())
                .build();
                
        orderMessagePublisher.publishOrderCreatedEvent(event);        
        
        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrder(Long id) {
        log.info("Fetching details for order ID: {}", id);
        
        // Fetch order or throw a basic runtime exception (we will add a global exception handler later)
        Order order = orderDal.findById(id)
                .orElseThrow(() -> {
                    log.error("Order ID: {} not found in database", id);
                    return new RuntimeException("Order not found");
                });
                
        return orderMapper.toResponseDto(order);
    }
}