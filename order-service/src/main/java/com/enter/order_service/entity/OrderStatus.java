package com.enter.order_service.entity;
/**
 * Represents the possible states of an order in the system.
 */
public enum OrderStatus {
    // Order was created, waiting for inventory service to check availability
    PENDING_INVENTORY, 
    
    // Inventory service confirmed and reserved the items
    CONFIRMED,         
    
    // Inventory service rejected the request (insufficient inventory)
    REJECTED           
}