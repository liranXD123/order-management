package com.enter.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for incoming order creation requests.
 * Uses Jakarta Validation to ensure the input is valid before processing.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    // Validates that the product ID is not null or empty
    @NotBlank(message = "Product ID cannot be empty or null")
    private String productId;

    // Validates that the quantity is a positive number (greater than 0)
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;
}