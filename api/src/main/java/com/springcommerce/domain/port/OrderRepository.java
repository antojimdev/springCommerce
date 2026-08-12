package com.springcommerce.domain.port;

import com.springcommerce.domain.entity.Order;

import java.util.Optional;
import java.util.UUID;

/**
 * OrderRepository Port (Interface)
 * 
 * This is a PORT in the Hexagonal Architecture (Ports & Adapters pattern).
 * It defines the contract for persisting Order aggregates.
 * 
 * The actual implementation (@Repository with Spring Data JPA) belongs
 * to the INFRASTRUCTURE layer, NOT here in the domain.
 * 
 * This ensures the domain is independent of any framework.
 * 
 * TODO: Implement the adapter in PHASE 4 (PostgreSQL Persistence)
 * Learning objective: Understand dependency inversion and ports
 * Questions to answer:
 * - Why should the domain NOT depend on Spring Data JPA?
 * - How does this enable testing without a database?
 * - What other ports might an Order aggregate need?
 */
public interface OrderRepository {
    
    // TODO: Define persistence operations in PHASE 4
    // - save(Order order): Order
    // - findById(UUID id): Optional<Order>
    // - findAll(): List<Order>
    // - update(Order order): Order
    // - delete(UUID id): void
    // - findByOrderNumber(String orderNumber): Optional<Order>
    
}
