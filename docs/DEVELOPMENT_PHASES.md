# Development Phases Roadmap

This document outlines the 17 phases of this educational project. Each phase builds on the previous one.

**Key Rule**: Do NOT implement logic from future phases early. Learn one concept at a time.

---

## Phase 1: ✅ Project Setup (COMPLETE)

**Objective**: Establish project structure, tooling, and CI/CD pipeline.

**What you get**:
- ✅ Maven project structure with Java 21
- ✅ Spring Boot 3.3.x skeleton
- ✅ Hexagonal Architecture folder layout
- ✅ Docker & Docker Compose setup
- ✅ GitHub Actions CI/CD pipeline
- ✅ Base documentation
- ✅ Test structure with JUnit 5

**What to learn**:
- Maven build system
- Spring Boot project setup
- Clean/Hexagonal Architecture principles
- Docker basics

**Next**: Implement domain layer

---

## Phase 2: Domain Layer (TODO)

**Objective**: Define the Order aggregate and domain concepts.

**What you'll implement**:
- Order entity with complete fields
- Order value objects (OrderStatus, Money, etc.)
- Domain services (if needed)
- Domain validation rules
- Domain exceptions

**What to learn**:
- Domain-Driven Design (DDD)
- Aggregates and Bounded Contexts
- Value Objects vs. Entities
- Ubiquitous Language

**Key Questions**:
- What are the essential attributes of an Order?
- What invariants must always be true?
- How do Orders relate to Customers?
- Should Orders have methods or be anemic?

**Before Phase 3**: Domain should be completely implementable, with rich logic and validation.

---

## Phase 3: REST API (TODO)

**Objective**: Create HTTP endpoints to interact with orders.

**What you'll implement**:
- OrderController with REST endpoints
  - POST /api/orders — Create order
  - GET /api/orders — List orders
  - GET /api/orders/{id} — Get order by ID
  - PUT /api/orders/{id} — Update order
  - DELETE /api/orders/{id} — Cancel order
- DTOs (CreateOrderRequest, CreateOrderResponse, etc.)
- Mappers (DTO ↔ Domain Entity)
- Input validation with Bean Validation
- Error handling (400, 422, 500 responses)
- Use cases (CreateOrderUseCase, GetOrderUseCase, etc.)

**What to learn**:
- Spring MVC and REST conventions
- HTTP status codes and semantics
- Data Transfer Objects (DTOs)
- Input validation and constraint violations
- Exception handling in Spring

**Testing**:
- MockMvc tests for controllers
- Use case tests with mocked repositories

**Before Phase 4**: REST API fully functional against in-memory repositories.

---

## Phase 4: PostgreSQL Persistence (TODO)

**Objective**: Persist orders to PostgreSQL database.

**What you'll implement**:
- OrderJpaEntity (JPA entity)
- OrderRepositoryJpaAdapter (implement OrderRepository port)
- Database schema design
- Migrations or Hibernate DDL strategy
- One-to-many relationships (Orders → OrderItems)
- Foreign key constraints
- Indexes for query performance

**What to learn**:
- JPA and Hibernate
- Relational database design
- N+1 query problems and prevention
- Database transactions and ACID properties
- Query optimization with indexes

**Concepts**:
- Impedance mismatch (OOP vs. Relational)
- Entity relationships (1:N, N:N)
- Query performance
- Transaction management

**Testing**:
- Integration tests with Testcontainers
- Real database queries
- Transaction behavior testing

**Before Phase 5**: Orders persist correctly, queries are efficient.

---

## Phase 5: Authentication (TODO)

**Objective**: Identify and authenticate users.

**What you'll implement**:
- User entity and repository
- Authentication controller (login endpoint)
- Password hashing (BCrypt)
- Principal and SecurityContext setup
- @AuthenticationPrincipal annotation usage

**What to learn**:
- Spring Security framework
- Password security and hashing
- Authentication vs. Authorization
- SecurityContext and Principal
- Session management

**Security Concepts**:
- Passwords should NEVER be stored plaintext
- One-way hashing vs. encryption
- Salt and how it prevents rainbow tables
- Brute-force protection

**Testing**:
- WithMockUser tests
- Authentication flow testing

**Before Phase 6**: Users can login and their identity is known to the system.

---

## Phase 6: Authorization (TODO)

**Objective**: Control what authenticated users can do.

**What you'll implement**:
- Roles (ADMIN, USER, MANAGER)
- Permissions (CREATE_ORDER, CANCEL_ORDER, etc.)
- @PreAuthorize and @PostAuthorize annotations
- Role-based access control (RBAC)
- Custom permission evaluators

**What to learn**:
- Authorization patterns
- Role-based access control (RBAC)
- Attribute-based access control (ABAC)
- Method-level security
- URL pattern security

**Security Concepts**:
- Principle of Least Privilege
- Role explosion problem
- Authorization boundaries

**Before Phase 7**: Users can only access resources they're authorized for.

---

## Phase 7: JWT + Refresh Tokens (TODO)

**Objective**: Implement stateless authentication with JSON Web Tokens.

**What you'll implement**:
- JWT token generation
- Token claims (subject, issued-at, expiration)
- Access token (short-lived, ~15 minutes)
- Refresh token (long-lived, ~7 days)
- Refresh endpoint
- Token validation and parsing
- JwtAuthenticationFilter

**What to learn**:
- JSON Web Tokens (JWT) structure and claims
- Stateless authentication
- Token expiration and refresh patterns
- Asymmetric vs. symmetric signing
- Token header and payload structure

**Security Concepts**:
- Tokens are NOT encrypted, only signed
- Tokens can be revoked (next phase)
- Token lifetime trade-offs
- Refresh token security

**Testing**:
- Token generation and validation
- Token expiration scenarios
- Refresh flow testing

**Before Phase 8**: Clients use JWT tokens instead of session IDs.

---

## Phase 8: Redis + Token Revocation (TODO)

**Objective**: Implement token blacklist for logout and security.

**What you'll implement**:
- Redis connection and Spring Data Redis
- Token blacklist on logout
- Token revocation checking in filter
- TTL-based expiration of blacklist entries
- Session tracking (optional)

**What to learn**:
- Redis data structures
- Key-value store operations
- TTL and expiration
- Caching strategies
- Cache invalidation

**Redis Concepts**:
- Sets for efficient lookup
- String operations
- EXPIRE and TTL commands
- Redis vs. database trade-offs

**Testing**:
- Redis integration tests with Testcontainers
- Logout and re-login flows

**Before Phase 9**: Users can securely logout, tokens are revokable.

---

## Phase 9: Orders Workflow (TODO)

**Objective**: Implement complete order lifecycle.

**What you'll implement**:
- Order status machine (PENDING → CONFIRMED → SHIPPED → DELIVERED)
- Status transitions and validations
- ConfirmOrderUseCase
- ShipOrderUseCase
- CancelOrderUseCase
- Order state preservation
- Idempotency keys (preview of Phase 12)

**What to learn**:
- State machines
- Workflow orchestration
- Business process modeling
- Idempotency concepts

**Concepts**:
- Valid state transitions
- Illegal state transitions (error handling)
- Workflows and process flows
- Event sourcing preview

**Testing**:
- State machine tests
- Workflow transition validation

**Before Phase 10**: Orders have meaningful lifecycle.

---

## Phase 10: RabbitMQ Setup (TODO)

**Objective**: Introduce event-driven architecture with RabbitMQ.

**What you'll implement**:
- RabbitMQ connection factory
- Exchange and queue configuration
- OrderCreatedEvent publisher
- OrderCreatedEvent consumer
- Message serialization (JSON)
- Routing keys and topic exchanges
- Error handling and retries

**What to learn**:
- Message brokers and event-driven architecture
- Producer-consumer pattern
- Topic vs. Direct exchanges
- Queues and bindings
- Message serialization

**RabbitMQ Concepts**:
- Exchanges (topic, direct, fanout)
- Queues and bindings
- Routing keys
- Message TTL

**Testing**:
- Integration tests with Testcontainers
- Message publishing and consumption
- Error scenario testing

**Before Phase 11**: Orders publish events when status changes.

---

## Phase 11: Event-Driven Workflow (TODO)

**Objective**: Orchestrate workflows through domain events.

**What you'll implement**:
- OrderCreatedEvent → Trigger inventory check
- OrderConfirmedEvent → Trigger payment processing
- OrderShippedEvent → Trigger notification
- Event handlers and subscribers
- Event ordering guarantees
- Compensation handlers (rollback on failure)

**What to learn**:
- Event-driven architecture patterns
- Choreography vs. Orchestration
- Saga pattern (distributed transactions)
- Event consistency
- Eventual consistency

**Concepts**:
- CQRS (Command Query Responsibility Segregation) preview
- Event sourcing concepts
- Distributed transactions
- Two-phase commit problems

**Before Phase 12**: System is event-driven, services communicate via events.

---

## Phase 12: Idempotency (TODO)

**Objective**: Ensure operations are safe to retry.

**What you'll implement**:
- Idempotency keys in requests
- Idempotency repository
- Idempotency middleware/filter
- Result caching for duplicate requests
- Client-side idempotency key generation

**What to learn**:
- Idempotency and idempotent operations
- Retry safety
- At-least-once delivery guarantees
- Deduplication strategies
- Distributed system resilience

**Concepts**:
- Safe retry logic
- Exactly-once vs. at-least-once semantics
- Deduplication windows
- Resource creation idempotency

**Testing**:
- Duplicate request testing
- Retry scenario testing

**Before Phase 13**: Operations can be retried safely without side effects.

---

## Phase 13: Transactional Outbox (TODO)

**Objective**: Ensure atomicity between database and message publishing.

**What you'll implement**:
- OutboxEvent table in database
- Save order AND outbox event in single transaction
- Background job to publish outbox events
- Polling publisher
- Event deduplication
- Failure handling and retry logic

**What to learn**:
- Distributed transactions
- Dual-write problem
- Transactional outbox pattern
- Event sourcing basics
- Saga pattern refinement

**Concepts**:
- ACID guarantees at application level
- Message order preservation
- Exactly-once semantics
- Cleanup and archival

**Testing**:
- Transaction boundary testing
- Failure and recovery scenarios

**Before Phase 14**: System is resilient to partial failures.

---

## Phase 14: Testing (TODO)

**Objective**: Comprehensive testing strategy across all layers.

**What you'll implement**:
- Unit tests for domain layer
- Integration tests for repository layer
- Application service tests
- Controller tests with MockMvc
- End-to-end tests with Testcontainers
- Test data builders (Test Fixtures)
- Test coverage reporting (JaCoCo)
- Contract tests (optional)

**What to learn**:
- Test pyramid (unit, integration, E2E)
- Testing strategies for different layers
- Testcontainers for integration testing
- Mocking vs. stubbing
- Test isolation and independence
- Performance testing

**Testing Concepts**:
- Given-When-Then structure
- Test coverage vs. quality
- Test maintainability
- Flaky test prevention

**Coverage Goals**:
- Domain layer: 100% coverage
- Application layer: 90%+ coverage
- Infrastructure: 80%+ coverage
- Interfaces: 70%+ coverage

**Before Phase 15**: Code is thoroughly tested.

---

## Phase 15: Docker & Containerization (TODO)

**Objective**: Package application in Docker images.

**What you'll implement**:
- Multi-stage Dockerfile
- Image optimization (layer caching, size)
- Health checks
- Kubernetes manifests (YAML)
- Container networking
- Volume management

**What to learn**:
- Docker images and layers
- Container best practices
- Security (non-root users, minimal images)
- Kubernetes basics
- Container orchestration

**Docker Concepts**:
- Image vs. container
- Layers and caching
- Volume mounting
- Port mapping
- Environment variables

**Testing**:
- Docker image verification
- Container startup testing

**Before Phase 16**: Application runs correctly in containers.

---

## Phase 16: CI/CD Pipeline (TODO)

**Objective**: Automate build, test, and deployment.

**What you'll implement**:
- GitHub Actions workflows
- Build and test stages
- Code coverage reporting
- Docker image building and pushing
- Automated deployment to staging
- Smoke tests on staging
- Approval gate for production
- Automated rollback (optional)

**What to learn**:
- CI/CD principles
- GitHub Actions workflows
- Container registry (DockerHub, GitHub Container Registry)
- Deployment strategies
- Blue-green deployment / canary deployments

**CI/CD Concepts**:
- Fail fast
- Artifact management
- Environment parity
- Deployment safety

**Before Phase 17**: Every push triggers automated testing and deployment.

---

## Phase 17: AWS Deployment (TODO)

**Objective**: Deploy to production on AWS.

**What you'll implement**:
- AWS account setup
- Elastic Container Service (ECS) or Elastic Kubernetes Service (EKS)
- RDS PostgreSQL (managed)
- ElastiCache Redis (managed)
- Application Load Balancer (ALB)
- Secrets Manager for sensitive data
- CloudWatch for logging and monitoring
- Auto-scaling configuration

**What to learn**:
- AWS services and architecture
- Infrastructure as Code (Terraform or CloudFormation)
- Managed services vs. self-hosted
- Auto-scaling and resilience
- Monitoring and observability
- Security in cloud (VPCs, security groups)

**AWS Concepts**:
- Availability Zones and Regions
- Elastic services
- Managed services benefits
- Cost optimization
- Disaster recovery

**Before Completion**: Application runs on AWS, scales automatically, and is monitored.

---

## Timeline & Effort

| Phase | Topic | Estimated Time |
|-------|-------|-----------------|
| 1 | Project Setup | ✅ 1-2 hours |
| 2 | Domain Layer | 4-6 hours |
| 3 | REST API | 4-6 hours |
| 4 | PostgreSQL | 6-8 hours |
| 5 | Authentication | 4-6 hours |
| 6 | Authorization | 4-6 hours |
| 7 | JWT | 4-6 hours |
| 8 | Redis | 4-6 hours |
| 9 | Orders Workflow | 6-8 hours |
| 10 | RabbitMQ | 6-8 hours |
| 11 | Event-Driven | 8-10 hours |
| 12 | Idempotency | 4-6 hours |
| 13 | Transactional Outbox | 6-8 hours |
| 14 | Testing | 8-10 hours |
| 15 | Docker | 4-6 hours |
| 16 | CI/CD | 4-6 hours |
| 17 | AWS | 10-12 hours |
| **TOTAL** | | ~100-130 hours |

---

## How to Proceed

### For Each Phase

1. **Read** the phase description carefully
2. **Understand** the learning objectives
3. **Answer** the "Key Questions"
4. **Implement** the features
5. **Test** thoroughly
6. **Review** your implementation against best practices
7. **Document** your architectural decisions
8. **Move** to next phase only when satisfied

### General Guidelines

- ✅ Write tests BEFORE implementation (TDD)
- ✅ Understand WHY before implementing WHAT
- ✅ Ask questions when uncertain
- ✅ Review Spring/database documentation
- ✅ Refactor when you see patterns
- ✅ Document architectural decisions
- ✅ Code review your own work

- ❌ Don't rush to the next phase
- ❌ Don't implement features from future phases
- ❌ Don't copy-paste code without understanding
- ❌ Don't skip testing
- ❌ Don't ignore error cases

---

## Learning Resources by Phase

### Phase 1-3: Foundational
- [Spring Boot Official Guide](https://spring.io/projects/spring-boot)
- [RESTful Web Services](https://martinfowler.com/articles/richardsonMaturityModel.html)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Phase 4: Database
- [PostgreSQL Tutorial](https://www.postgresql.org/docs/current/tutorial.html)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [Database Design Fundamentals](https://www.coursera.org/courses?query=database%20design)

### Phase 5-8: Security
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [JWT.io](https://jwt.io/)

### Phase 9-13: Event-Driven
- [RabbitMQ Tutorials](https://www.rabbitmq.com/getstarted.html)
- [Saga Pattern](https://microservices.io/patterns/data/saga.html)
- [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html)

### Phase 14-17: Operations
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [GitHub Actions](https://docs.github.com/en/actions)
- [AWS Architecture](https://aws.amazon.com/architecture/)

---

**Remember**: This is a learning journey. Take time to understand concepts deeply. The goal is not speed, but mastery.

Happy learning! 🚀
