package com.springcommerce.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * OrderRepositoryIntegrationTest - Infrastructure Layer Integration Tests
 * 
 * This test class will use Testcontainers to run a real PostgreSQL database
 * for integration testing the OrderRepository adapter.
 * 
 * Testcontainers spins up a containerized database for each test run,
 * providing true integration testing without mocking the database.
 * 
 * TODO: Implement integration tests in PHASE 4 (PostgreSQL Persistence)
 * Learning objective: Integration testing with Testcontainers
 * Questions to answer:
 * - How does Testcontainers make testing easier?
 * - What's the difference between unit tests and integration tests?
 * - How do we set up database state for tests?
 * - How do we verify database changes?
 */
@Testcontainers
@DisplayName("OrderRepository Integration Tests")
class OrderRepositoryIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_password");
    
    /**
     * TODO: Test save operation in PHASE 4
     * Given: A new Order
     * When: OrderRepository.save() is called
     * Then: Order is persisted to database
     */
    
    /**
     * TODO: Test find operation in PHASE 4
     * Given: An Order exists in database
     * When: OrderRepository.findById() is called
     * Then: Order is retrieved correctly
     */
    
    /**
     * TODO: Test update operation in PHASE 4
     */
    
    /**
     * TODO: Test delete operation in PHASE 4
     */
}
