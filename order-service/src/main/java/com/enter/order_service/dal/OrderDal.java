package com.enter.order_service.dal;

import com.enter.order_service.entity.Order;
import java.util.Optional;

/**
 * Data Access Layer (DAL) interface for Orders.
 * Acts as an intermediary between the Service layer and the Repository.
 * This ensures the Service layer is not tightly coupled to JPA.
 */
public interface OrderDal {
    
    /**
     * Persists an order to the database.
     * 
     * @param order the order entity to be saved
     * @return the saved order entity with its generated ID
     */
    Order save(Order order);

    /**
     * Retrieves an order by its unique identifier.
     * 
     * @param id the order ID
     * @return an Optional containing the Order if found, otherwise empty
     */
    Optional<Order> findById(Long id);
}