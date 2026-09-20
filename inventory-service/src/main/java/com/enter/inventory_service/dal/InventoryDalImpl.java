package com.enter.inventory_service.dal;

import com.enter.inventory_service.entity.Inventory;
import com.enter.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryDalImpl implements InventoryDal {

    private final InventoryRepository inventoryRepository;

    @Override
    public Optional<Inventory> findByProductId(String productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}