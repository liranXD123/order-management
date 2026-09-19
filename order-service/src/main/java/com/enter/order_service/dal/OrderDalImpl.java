package com.enter.order_service.dal;

import com.enter.order_service.entity.Order;
import com.enter.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of the Order Data Access Layer.
 * Injects the OrderRepository to perform the actual database operations.
 */
@Component
@RequiredArgsConstructor
public class OrderDalImpl implements OrderDal {

    // RequiredArgsConstructor from Lombok automatically creates a constructor 
    // to inject this final field.
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        // Delegates the save operation to the JPA Repository
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        // Delegates the fetch operation to the JPA Repository
        return orderRepository.findById(id);
    }
}