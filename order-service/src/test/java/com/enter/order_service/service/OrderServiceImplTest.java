package com.enter.order_service.service;

import com.enter.order_service.dal.OrderDal;
import com.enter.order_service.dto.OrderRequestDto;
import com.enter.order_service.dto.OrderResponseDto;
import com.enter.order_service.entity.Order;
import com.enter.order_service.entity.OrderStatus;
import com.enter.order_service.mapper.OrderMapper;
import com.enter.order_service.messaging.OrderMessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderServiceImpl using JUnit 5 and Mockito.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    // Mocking the dependencies so we don't need a real DB or RabbitMQ
    @Mock
    private OrderDal orderDal;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderMessagePublisher orderMessagePublisher;

    // Injecting the mocks into the service we actually want to test
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_ShouldSaveOrderAndPublishEvent() {
        // 1. Arrange: Setup our mock behaviors and test data
        OrderRequestDto request = OrderRequestDto.builder().productId("LAPTOP-X1").quantity(2).build();
        Order mappedEntity = Order.builder().productId("LAPTOP-X1").quantity(2).status(OrderStatus.PENDING_INVENTORY).build();
        Order savedEntity = Order.builder().id(1L).productId("LAPTOP-X1").quantity(2).status(OrderStatus.PENDING_INVENTORY).build();
        OrderResponseDto expectedResponse = OrderResponseDto.builder().id(1L).productId("LAPTOP-X1").quantity(2).status(OrderStatus.PENDING_INVENTORY).build();

        // When the service calls the mapper or DAL, return our predefined objects
        when(orderMapper.toEntity(request)).thenReturn(mappedEntity);
        when(orderDal.save(any(Order.class))).thenReturn(savedEntity);
        when(orderMapper.toResponseDto(savedEntity)).thenReturn(expectedResponse);

        // 2. Act: Call the method we want to test
        OrderResponseDto actualResponse = orderService.createOrder(request);

        // 3. Assert: Verify the results match our expectations
        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getId());
        assertEquals(OrderStatus.PENDING_INVENTORY, actualResponse.getStatus());        
        // Verify that the publisher was called exactly once to send the message to RabbitMQ
        verify(orderMessagePublisher, times(1)).publishOrderCreatedEvent(any());
    }

    @Test
    void getOrder_WhenOrderNotFound_ShouldThrowException() {
        // 1. Arrange: Simulate DB returning empty
        Long orderId = 99L;
        when(orderDal.findById(orderId)).thenReturn(Optional.empty());

        // 2 & 3. Act & Assert: Verify that a RuntimeException is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrder(orderId);
        });
        
        assertEquals("Order not found", exception.getMessage());
    }
}