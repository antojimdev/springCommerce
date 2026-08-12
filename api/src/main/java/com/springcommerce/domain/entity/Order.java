package com.springcommerce.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Order Entity - Domain Model
 * 
 * This is a pure domain entity with NO Spring annotations.
 * It belongs to the DOMAIN layer which must be framework-agnostic.
 * 
 * TODO: Implement Order fields in PHASE 2 (Domain)
 * Learning objective: Understand ubiquitous language and domain-driven design
 * Questions to answer:
 * - What are the essential attributes of an Order?
 * - What invariants must an Order maintain?
 * - Should Order have methods or be anemic?
 * - How does Order relate to other domain concepts?
 */
@Data
@AllArgsConstructor
@Builder
public class Order {
    
    private UUID id;
    private String orderNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // TODO: Add order-specific fields in PHASE 2
    // - customerId
    // - status (enum: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
    // - totalAmount
    // - items (List<OrderItem>)
    // - shippingAddress
    // - billingAddress
    // - paymentMethod
    
}
