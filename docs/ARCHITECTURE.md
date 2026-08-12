# Clean/Hexagonal Architecture Guide

## Overview

This project uses **Hexagonal Architecture** (also called **Ports & Adapters** or **Clean Architecture**).

The goal: **Make the domain independent of frameworks, databases, and external services.**

## Core Principle

```
Dependencies always flow INWARD toward the Domain.
The Domain depends on NOTHING external.
```

## The Four Layers

### 1️⃣ **DOMAIN Layer** (Core Business Logic)

**Location**: `src/main/java/com/springcommerce/domain/`

**What goes here**:
- ✅ Entities (Order, Customer, etc.)
- ✅ Value Objects (Money, OrderStatus, etc.)
- ✅ Domain Services (business logic)
- ✅ **Ports** (interfaces defining contracts)
- ✅ Domain Exceptions
- ✅ Domain Events

**What's FORBIDDEN here**:
- ❌ Spring annotations (@Entity, @Autowired, etc.)
- ❌ Database-specific code (SQL, JPA)
- ❌ HTTP/REST (Controllers, @RequestMapping)
- ❌ External library dependencies (except data types)

**Why**: The domain should be framework-independent. You should be able to use the domain logic in a CLI app, or with a different framework, without changes.

### 2️⃣ **APPLICATION Layer** (Use Cases & Orchestration)

**Location**: `src/main/java/com/springcommerce/application/`

**What goes here**:
- ✅ Use Cases (e.g., CreateOrderUseCase)
- ✅ Application Services
- ✅ Input/Output Data structures for use cases
- ✅ Dependency injection of domain ports
- ✅ Orchestration logic (calling domain entities and ports)

**What's NOT here**:
- ❌ Business logic (belongs in domain)
- ❌ HTTP/REST specifics
- ❌ Direct database access (use ports)

**Why**: Application layer coordinates domain logic. It orchestrates how domain entities and services are used.

### 3️⃣ **INTERFACES Layer** (Delivery Mechanisms)

**Location**: `src/main/java/com/springcommerce/interfaces/`

**What goes here**:
- ✅ Controllers (Spring @RestController)
- ✅ DTOs (Data Transfer Objects for HTTP)
- ✅ Mappers (DTO ↔ Domain Entity conversion)
- ✅ HTTP-specific validation
- ✅ Request/Response marshalling

**What's FORBIDDEN here**:
- ❌ Business logic
- ❌ Direct database access
- ❌ Complex transformations

**Why**: Controllers are thin. They receive HTTP requests, delegate to application layer, convert responses to DTOs, and return to client.

### 4️⃣ **INFRASTRUCTURE Layer** (Implementations & Adapters)

**Location**: `src/main/java/com/springcommerce/infrastructure/`

**What goes here**:
- ✅ Repository implementations (e.g., OrderRepositoryJpaAdapter)
- ✅ Database-specific code (JPA, SQL)
- ✅ Messaging implementations (RabbitMQ publisher)
- ✅ External service integrations
- ✅ Cache implementations
- ✅ Configuration classes

**Principle**: Infrastructure implements the **ports** defined in the domain.

**Why**: By implementing domain ports in infrastructure, we can swap implementations. Need different database? Create a new adapter. Need different message broker? Create a new adapter.

## Dependency Flow

```
INTERFACES
    ↓ (depends on)
APPLICATION
    ↓ (depends on)
DOMAIN
    ↓ (defines ports that)
INFRASTRUCTURE (implements)
```

**NEVER go up**: Domain should never import from Application, Application should never import from Interfaces.

## Example: Creating an Order

### 1. Request comes to Controller (INTERFACES)

```java
@PostMapping
public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
    // Receive HTTP request, validate DTO
    CreateOrderResponse response = createOrderUseCase.execute(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

### 2. Use Case orchestrates (APPLICATION)

```java
public class CreateOrderUseCase {
    private final OrderRepository repository;
    private final OrderPublisher publisher;
    
    public CreateOrderResponse execute(CreateOrderRequest request) {
        // 1. Create domain entity
        Order order = Order.create(request.getCustomerId(), request.getItems());
        
        // 2. Persist through PORT (not knowing how)
        Order saved = repository.save(order);
        
        // 3. Publish event through PORT
        publisher.publish(new OrderCreatedEvent(saved.getId()));
        
        // 4. Return DTO
        return mapper.toResponse(saved);
    }
}
```

### 3. Domain logic (DOMAIN)

```java
public class Order {
    private UUID id;
    private CustomerId customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    
    public static Order create(CustomerId customerId, List<OrderItem> items) {
        // Validate business rules
        if (items.isEmpty()) {
            throw new DomainException("Order must have items");
        }
        
        // Create entity
        return Order.builder()
            .id(UUID.randomUUID())
            .customerId(customerId)
            .items(items)
            .status(OrderStatus.PENDING)
            .build();
    }
}
```

### 4. Repository implements PORT (INFRASTRUCTURE)

```java
@Repository
public class OrderRepositoryJpaAdapter implements OrderRepository {
    private final OrderJpaRepository jpa;
    
    @Override
    public Order save(Order order) {
        // Convert domain entity to JPA entity
        OrderJpaEntity entity = mapper.toPersistenceEntity(order);
        
        // Persist with Spring Data
        jpa.save(entity);
        
        // Convert back to domain entity
        return mapper.toDomainEntity(entity);
    }
}
```

## Ports & Adapters Pattern

A **PORT** is an interface in the domain defining a contract.

An **ADAPTER** is an implementation of that port in the infrastructure.

### Order Repository Example

**PORT (Domain)**:
```java
// src/main/java/com/springcommerce/domain/port/OrderRepository.java
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}
```

**ADAPTER (Infrastructure)**:
```java
// src/main/java/com/springcommerce/infrastructure/persistence/OrderRepositoryJpaAdapter.java
@Repository
public class OrderRepositoryJpaAdapter implements OrderRepository {
    // JPA-specific implementation
}
```

**Why**: Tomorrow, if we want to use MongoDB instead of PostgreSQL:
1. Create `OrderRepositoryMongoAdapter` implementing the same port
2. Update Spring configuration to use new adapter
3. Domain and Application layers don't change!

## Testing Benefits

Each layer can be tested independently:

```java
// Unit Test - DOMAIN (no frameworks needed)
Order order = Order.create(customerId, items);
assert order.getStatus() == OrderStatus.PENDING;

// Unit Test - APPLICATION (mock ports)
@Mock OrderRepository repository;
CreateOrderUseCase useCase = new CreateOrderUseCase(repository, publisher);
CreateOrderResponse response = useCase.execute(request);

// Integration Test - INFRASTRUCTURE (real database)
@SpringBootTest
OrderRepositoryJpaAdapter adapter = new OrderRepositoryJpaAdapter(jpaRepository);
Order saved = adapter.save(order);
assert saved.getId() != null;

// E2E Test - ALL LAYERS (full app)
mockMvc.perform(post("/api/orders").content(json))
    .andExpect(status().isCreated());
```

## Folder Structure

```
api/
├── src/main/java/com/springcommerce/
│   ├── domain/                    # Framework-independent core
│   │   ├── entity/                # Aggregates, Value Objects
│   │   │   └── Order.java
│   │   ├── port/                  # Contracts for external concerns
│   │   │   ├── OrderRepository.java
│   │   │   └── OrderPublisher.java
│   │   ├── exception/             # Domain exceptions
│   │   │   └── DomainException.java
│   │   └── service/               # Domain services (if needed)
│   │
│   ├── application/               # Use cases & orchestration
│   │   ├── usecase/               # Business flows
│   │   │   ├── CreateOrderUseCase.java
│   │   │   ├── GetOrderUseCase.java
│   │   │   └── ListOrdersUseCase.java
│   │   └── service/               # Application services
│   │       └── OrderApplicationService.java
│   │
│   ├── interfaces/                # HTTP delivery & external world
│   │   ├── controller/            # REST controllers
│   │   │   └── OrderController.java
│   │   ├── dto/                   # HTTP data structures
│   │   │   ├── CreateOrderRequest.java
│   │   │   └── CreateOrderResponse.java
│   │   └── mapper/                # DTO ↔ Entity conversion
│   │       └── OrderMapper.java
│   │
│   └── infrastructure/            # Implementations & adapters
│       ├── persistence/           # Database adapters
│       │   ├── OrderRepositoryJpaAdapter.java
│       │   └── OrderJpaRepository.java
│       ├── messaging/             # Messaging adapters
│       │   └── OrderPublisherRabbitMqAdapter.java
│       ├── config/                # Spring configuration
│       │   └── PersistenceConfig.java
│       └── entity/                # JPA entities (internal to infra)
│           └── OrderJpaEntity.java
│
└── src/test/
    ├── java/com/springcommerce/
    │   ├── domain/                # Domain unit tests
    │   ├── application/           # Application tests with mocks
    │   └── interfaces/            # Controller tests
    │
    └── resources/
        └── application-test.yml   # Test configuration
```

## Dependency Injection

Use constructor injection to make dependencies explicit:

```java
// ✅ GOOD: Dependencies are explicit and testable
public class CreateOrderUseCase {
    private final OrderRepository repository;
    private final OrderPublisher publisher;
    
    public CreateOrderUseCase(OrderRepository repository, OrderPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }
}

// ❌ BAD: Hidden dependencies, hard to test
public class CreateOrderUseCase {
    @Autowired
    private OrderRepository repository;
}
```

## Common Questions

### Q: Can I use Spring annotations in the domain?
**A**: No. Domain should be framework-agnostic. If domain depends on Spring, you can't use it with other frameworks.

### Q: Where do I put repository methods?
**A**: Define interface (PORT) in `domain/port/`. Implement it (ADAPTER) in `infrastructure/persistence/`.

### Q: Should entities have business logic?
**A**: Yes! Entities should encapsulate business rules. Keep them rich, not anemic.

### Q: How do I test without a database?
**A**: Mock the repository port. Your domain doesn't know about databases, so tests don't need them.

### Q: What's the difference between Domain Service and Application Service?
**A**: **Domain Service**: orchestrates domain entities and concepts. Lives in domain. **Application Service**: orchestrates domain services and ports. Lives in application.

## Resources

- [Clean Architecture by Robert Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture by Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design by Eric Evans](https://www.domainlanguage.com/ddd/)
