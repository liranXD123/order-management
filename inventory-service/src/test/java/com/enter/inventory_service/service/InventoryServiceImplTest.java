package com.enter.inventory_service.service;

import com.enter.inventory_service.dal.InventoryDal;
import com.enter.inventory_service.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InventoryServiceImpl using JUnit 5 and Mockito.
 */
@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryDal inventoryDal;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void checkAndReserveInventory_WhenStockIsSufficient_ShouldReturnTrueAndDeductStock() {
        // Arrange: Setup existing inventory with 10 items
        String productId = "LAPTOP-X1";
        Inventory existingInventory = Inventory.builder().id(1L).productId(productId).quantity(10).build();
        when(inventoryDal.findByProductId(productId)).thenReturn(Optional.of(existingInventory));
        
        // Act: Try to reserve 2 items
        boolean result = inventoryService.checkAndReserveInventory(productId, 2);
        
        // Assert: Verify it succeeded and quantity was deducted to 8
        assertTrue(result);
        assertEquals(8, existingInventory.getQuantity());
        
        // Verify that the updated inventory was saved to the DB via DAL
        verify(inventoryDal, times(1)).save(existingInventory);
    }

    @Test
    void checkAndReserveInventory_WhenStockIsInsufficient_ShouldReturnFalse() {
        // Arrange: Setup existing inventory with only 1 item
        String productId = "LAPTOP-X1";
        Inventory existingInventory = Inventory.builder().id(1L).productId(productId).quantity(1).build();
        when(inventoryDal.findByProductId(productId)).thenReturn(Optional.of(existingInventory));
        
        // Act: Try to reserve 2 items
        boolean result = inventoryService.checkAndReserveInventory(productId, 2);
        
        // Assert: Verify it failed and quantity remained 1
        assertFalse(result);
        assertEquals(1, existingInventory.getQuantity());
        
        // Verify that save was NEVER called
        verify(inventoryDal, never()).save(any());
    }
}