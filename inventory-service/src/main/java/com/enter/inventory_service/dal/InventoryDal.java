package com.enter.inventory_service.dal;

import com.enter.inventory_service.entity.Inventory;
import java.util.Optional;

/**
 * Data Access Layer (DAL) interface for Inventory.
 * Isolates the Service layer from direct Spring Data JPA usage.
 */
public interface InventoryDal {
    
    Optional<Inventory> findByProductId(String productId);
    
    Inventory save(Inventory inventory);
}