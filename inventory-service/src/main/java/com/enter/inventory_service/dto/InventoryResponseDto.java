package com.enter.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for outgoing inventory responses.
 * Ensures the database entity is not directly exposed through the API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {
    private String productId;
    private Integer quantity;
}