package com.enter.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the 'orders' table in the database.
 * Note: We name the table 'orders' because 'order' is a reserved keyword in SQL.
 * As per requirements, we will NOT expose this Entity directly through the API.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the order

    @Column(nullable = false)
    private String productId; // The ID of the product being ordered

    @Column(nullable = false)
    private Integer quantity; // How many items are requested

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; // The current status of the order (e.g., PENDING_INVENTORY)
}