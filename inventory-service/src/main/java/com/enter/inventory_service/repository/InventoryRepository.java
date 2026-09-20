package com.enter.inventory_service.repository;

import com.enter.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for database operations on the Inventory entity.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    /**
     * Custom query method to find an inventory record by its product ID.
     * Spring Data JPA automatically generates the SQL query for this.
     */
    Optional<Inventory> findByProductId(String productId);
}