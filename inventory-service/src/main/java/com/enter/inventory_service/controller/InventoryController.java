package com.enter.inventory_service.controller;

import com.enter.inventory_service.dto.InventoryResponseDto;
import com.enter.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for inventory operations.
 * Exposes endpoint to check current stock levels.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Endpoint to retrieve current inventory for a specific product.
     * Required by the business scenario documentation.
     */
    @GetMapping("/{productid}")
    public ResponseEntity<InventoryResponseDto> getInventory(@PathVariable("productid") String productId) {
        log.info("Received HTTP GET request for inventory of product: {}", productId);
        InventoryResponseDto response = inventoryService.getInventory(productId);
        return ResponseEntity.ok(response);
    }
}