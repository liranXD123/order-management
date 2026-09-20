package com.enter.inventory_service.service;

import com.enter.inventory_service.dto.InventoryResponseDto;

/**
 * Service interface for handling inventory business logic.
 */
public interface InventoryService {

    /**
     * Checks if requested quantity is available and reserves it by deducting from stock.
     * 
     * @param productId the ID of the product
     * @param quantity the amount requested
     * @return true if successfully reserved, false if insufficient stock or product not found
     */
    boolean checkAndReserveInventory(String productId, Integer quantity);

    /**
     * Retrieves current inventory levels for a specific product.
     * 
     * @param productId the ID of the product
     * @return DTO containing current stock levels
     */
    InventoryResponseDto getInventory(String productId);
}