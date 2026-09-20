package com.enter.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the 'inventory' table in the database.
 * Holds the available quantity for a specific product.
 */
@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The product identifier, marked as unique since each product has one inventory record
    @Column(nullable = false, unique = true)
    private String productId;

    // The amount of items currently available in stock
    @Column(nullable = false)
    private Integer quantity;
}