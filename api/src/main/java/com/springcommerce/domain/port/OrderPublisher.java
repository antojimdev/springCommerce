package com.springcommerce.domain.port;

import com.springcommerce.domain.entity.Order;

/**
 * OrderPublisher Port (Interface)
 * 
 * This is another PORT in Hexagonal Architecture.
 * It defines the contract for publishing Order domain events.
 * 
 * The actual implementation (RabbitMQ producer) belongs to INFRASTRUCTURE.
 * 
 * This enables event-driven architecture while keeping domain clean.
 * 
 * TODO: Implement the adapter in PHASE 10 (RabbitMQ)
 * Learning objective: Understand event-driven architecture and messaging
 * Questions to answer:
 * - What domain events should an Order publish?
 * - When should these events be published?
 * - How do we ensure event ordering and delivery guarantees?
 * - What is the role of Transactional Outbox (PHASE 13)?
 */
public interface OrderPublisher {
    
    // TODO: Define event publishing operations in PHASE 10
    // - publishOrderCreatedEvent(Order order): void
    // - publishOrderConfirmedEvent(Order order): void
    // - publishOrderShippedEvent(Order order): void
    // - publishOrderCancelledEvent(Order order): void
    
}
