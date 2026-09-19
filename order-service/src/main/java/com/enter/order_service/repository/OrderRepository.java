package com.enter.order_service.repository;

import com.enter.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the Order entity.
 * Extends JpaRepository to inherit standard CRUD operations (save, findById, etc.).
 * This layer directly interacts with Spring Data JPA and the underlying database.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository provides all basic methods out of the box.
    // Custom query methods can be added here if needed in the future.
}