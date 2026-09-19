package com.enter.order_service.controller;

import com.enter.order_service.dto.OrderRequestDto;
import com.enter.order_service.dto.OrderResponseDto;
import com.enter.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing orders.
 * Exposes endpoints for order creation and retrieval.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Endpoint to create a new order.
     * Uses @Valid to trigger Jakarta Validation constraints defined in OrderRequestDto.
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        log.info("Received HTTP POST request to create an order");
        OrderResponseDto response = orderService.createOrder(requestDto);
        
        // Returns HTTP 201 (Created) along with the generated response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint to fetch an existing order by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        log.info("Received HTTP GET request for order ID: {}", id);
        OrderResponseDto response = orderService.getOrder(id);
        
        // Returns HTTP 200 (OK) with the order details
        return ResponseEntity.ok(response);
    }
}