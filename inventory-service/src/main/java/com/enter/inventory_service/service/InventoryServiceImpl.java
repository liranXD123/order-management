package com.enter.inventory_service.service;

import com.enter.inventory_service.dal.InventoryDal;
import com.enter.inventory_service.dto.InventoryResponseDto;
import com.enter.inventory_service.entity.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDal inventoryDal; // Dependency injection for InventoryDal to interact with the database

    @Override
    public boolean checkAndReserveInventory(String productId, Integer quantity) {
        log.info("Checking inventory for product: {} (Requested quantity: {})", productId, quantity);
        
        Optional<Inventory> inventoryOpt = inventoryDal.findByProductId(productId);
        
        if (inventoryOpt.isEmpty()) {
            log.warn("Product {} not found in inventory database", productId);
            return false;
        }
        
        Inventory inventory = inventoryOpt.get();
        
        if (inventory.getQuantity() >= quantity) {
            // Fulfills the requirement to reserve inventory if available
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryDal.save(inventory);
            
            log.info("Successfully reserved {} units of {}. Remaining stock: {}", 
                     quantity, productId, inventory.getQuantity());
            return true;
        } else {
            // Insufficient inventory scenario
            log.warn("Insufficient stock for product {}. Available: {}, Requested: {}", 
                     productId, inventory.getQuantity(), quantity);
            return false;
        }
    }

    @Override
    public InventoryResponseDto getInventory(String productId) {
        log.info("Fetching inventory for product: {}", productId);
        
        Inventory inventory = inventoryDal.findByProductId(productId)
                .orElseThrow(() -> {
                    log.error("Product {} not found", productId);
                    return new RuntimeException("Product not found in inventory");
                });
                
        return InventoryResponseDto.builder()
                .productId(inventory.getProductId())
                .quantity(inventory.getQuantity())
                .build();
    }
}