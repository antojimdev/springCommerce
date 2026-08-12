**Agregar a la raiz del proyecto el archivo .github/copilot-instructions.md**

# PROMPT — Estructura base del proyecto Backend

Actúa como un **Senior Backend Engineer especializado en Java, Spring Boot y arquitectura de sistemas**.

Quiero crear un proyecto de portfolio llamado:

**Order Management & Processing API**

El objetivo NO es que implementes la aplicación completa.

Quiero que construyas únicamente la **estructura inicial del proyecto, carpetas, clases base, configuraciones mínimas y documentación**, dejando la implementación de la lógica de negocio para que yo la complete manualmente.

La finalidad es que este proyecto me sirva para **aprender y demostrar conocimientos de Backend Engineer** después de 5 años de experiencia profesional.

---

# 1. STACK TECNOLÓGICO

Utiliza:

* Java 21
* Spring Boot 4.x
* Maven
* Spring Web
* Spring Validation
* Spring Data JPA
* PostgreSQL
* Spring Security
* JWT
* Redis
* RabbitMQ
* Docker
* Docker Compose
* JUnit 5
* Mockito
* Testcontainers
* GitHub Actions

No añadas frameworks adicionales salvo que exista una razón clara.

---

# 2. OBJETIVO PRINCIPAL

El proyecto será una API REST para gestionar:

* usuarios
* roles
* productos
* pedidos
* líneas de pedido
* pagos
* inventario
* eventos

El flujo principal será:

```text
User
  ↓
Create Order
  ↓
Validate products
  ↓
Check inventory
  ↓
Persist Order
  ↓
Create Outbox Event
  ↓
RabbitMQ
  ↓
Inventory
  ↓
Payment
  ↓
Notification
```

Inicialmente debe ser un:

**MODULAR MONOLITH**

No quiero múltiples aplicaciones Spring Boot.

La arquitectura debe estar preparada para que determinados módulos puedan separarse posteriormente en microservicios.

---

# 3. ARQUITECTURA

Utiliza una arquitectura inspirada en:

**Clean Architecture / Hexagonal Architecture**

Quiero separar claramente:

```text
interfaces
application
domain
infrastructure
```

La regla fundamental es:

```text
Domain
  ↑
Application
  ↑
Interfaces / Infrastructure
```

El dominio NO debe depender directamente de:

* Spring
* PostgreSQL
* RabbitMQ
* Redis
* HTTP

Evita introducir lógica de negocio dentro de Controllers.

Evita que los Controllers llamen directamente a Repositories.

---

# 4. ESTRUCTURA DE CARPETAS

Crea una estructura similar a:

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/orders/
│   │       ├── OrderApplication.java
│   │       │
│   │       ├── domain/
│   │       │   ├── model/
│   │       │   ├── repository/
│   │       │   ├── event/
│   │       │   ├── exception/
│   │       │   └── service/
│   │       │
│   │       ├── application/
│   │       │   ├── usecase/
│   │       │   ├── dto/
│   │       │   │   ├── request/
│   │       │   │   └── response/
│   │       │   └── mapper/
│   │       │
│   │       ├── interfaces/
│   │       │   └── rest/
│   │       │       ├── auth/
│   │       │       ├── users/
│   │       │       ├── products/
│   │       │       └── orders/
│   │       │
│   │       └── infrastructure/
│   │           ├── persistence/
│   │           ├── security/
│   │           ├── messaging/
│   │           ├── cache/
│   │           └── configuration/
│   │
│   └── resources/
│       ├── application.yml
│       └── db/
│           └── migration/
│
└── test/
    └── java/
        └── com/example/orders/
```

Puedes modificar esta estructura si consideras que existe una alternativa mejor, pero explica el motivo.

---

# 5. DOMAIN

Crea únicamente las clases base necesarias para representar el dominio.

Entidades principales:

```text
User
Role
Product
Order
OrderItem
Payment
```

También prepara los conceptos necesarios para:

```text
OrderStatus
PaymentStatus
UserRole
```

No implementes todavía lógica compleja.

No generes automáticamente todos los getters/setters si existe una alternativa más limpia.

Utiliza características modernas de Java 21 cuando tengan sentido.

---

# 6. REPOSITORIES

Define las interfaces necesarias en el dominio para trabajar con:

```text
UserRepository
ProductRepository
OrderRepository
PaymentRepository
```

IMPORTANTE:

Estas interfaces pertenecen al dominio/application layer.

La implementación concreta utilizando Spring Data JPA debe estar en:

```text
infrastructure/persistence
```

No quiero que el dominio dependa de `JpaRepository`.

Quiero aprender Dependency Inversion.

---

# 7. APPLICATION / USE CASES

Crea las interfaces o clases base para los principales casos de uso:

```text
RegisterUserUseCase
AuthenticateUserUseCase
RefreshTokenUseCase
LogoutUserUseCase

CreateProductUseCase
GetProductUseCase
UpdateProductUseCase
DeleteProductUseCase

CreateOrderUseCase
GetOrderUseCase
GetUserOrdersUseCase
CancelOrderUseCase
```

NO IMPLEMENTES LA LÓGICA.

Los métodos pueden contener:

```java
throw new UnsupportedOperationException("TODO");
```

o una estructura equivalente.

Quiero ser yo quien implemente los casos de uso.

---

# 8. DTOs

Crea DTOs separados de las entidades de dominio.

Por ejemplo:

```text
RegisterUserRequest
LoginRequest
RefreshTokenRequest

AuthResponse

CreateProductRequest
UpdateProductRequest
ProductResponse

CreateOrderRequest
OrderItemRequest
OrderResponse
```

Añade únicamente las anotaciones de validación que tengan sentido.

Por ejemplo:

```java
@NotBlank
@Email
@NotNull
@Positive
```

No implementes todavía validaciones complejas.

---

# 9. REST CONTROLLERS

Crea Controllers básicos:

```text
AuthController
UserController
ProductController
OrderController
```

Define únicamente los endpoints y firmas de los métodos.

Por ejemplo:

```text
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/refresh
POST /api/v1/auth/logout

GET /api/v1/users/me

GET /api/v1/products
GET /api/v1/products/{id}
POST /api/v1/products
PUT /api/v1/products/{id}
PATCH /api/v1/products/{id}
DELETE /api/v1/products/{id}

POST /api/v1/orders
GET /api/v1/orders
GET /api/v1/orders/{id}
PATCH /api/v1/orders/{id}/cancel
```

Los Controllers NO deben implementar lógica de negocio.

Deja TODOs donde deba intervenir yo.

---

# 10. SECURITY

Prepara la estructura para:

```text
Spring Security
JWT
Access Token
Refresh Token
Roles
Authorization
Authentication
```

Crea únicamente las clases estructurales necesarias, por ejemplo:

```text
SecurityConfig
JwtAuthenticationFilter
JwtTokenProvider
CustomUserDetailsService
```

Pero NO implementes todavía toda la seguridad.

Especialmente quiero implementar yo:

* generación de JWT
* validación
* expiración
* refresh tokens
* autorización por roles
* logout
* revocación
* blacklist
* JTI

Añade TODOs explicativos indicando qué debo implementar.

---

# 11. REDIS

Prepara la infraestructura para Redis.

Necesitaremos Redis para:

1. Token revocation
2. Refresh tokens
3. Cache
4. Posiblemente rate limiting en una fase posterior

Crea únicamente interfaces/abstracciones y configuración mínima.

NO implementes todavía la blacklist.

Quiero aprender yo cómo funciona.

Añade un TODO explicando:

* qué es un JTI
* cómo almacenar un token revocado
* cómo utilizar TTL
* por qué no debemos guardar tokens indefinidamente

---

# 12. RABBITMQ

Prepara la arquitectura para eventos.

Crea:

```text
domain/event/
```

y prepara eventos como:

```text
OrderCreatedEvent
InventoryReservedEvent
InventoryRejectedEvent
PaymentRequestedEvent
PaymentCompletedEvent
PaymentFailedEvent
OrderConfirmedEvent
```

En infrastructure:

```text
messaging/
├── publisher/
├── consumer/
└── configuration/
```

NO implementes todavía los consumidores.

Crea interfaces y clases base con TODOs.

Quiero implementar yo:

* exchanges
* queues
* routing keys
* consumers
* publishers
* retry
* dead-letter queues
* idempotency
* correlation ID

---

# 13. TRANSACTIONAL OUTBOX

Prepara una estructura inicial para:

```text
OutboxEvent
OutboxRepository
OutboxPublisher
```

No implementes el patrón.

Añade un archivo:

```text
docs/OUTBOX_PATTERN.md
```

explicando:

* qué problema intenta solucionar
* por qué guardar DB + publicar RabbitMQ puede provocar inconsistencias
* cómo funciona el patrón
* qué debo implementar
* posibles problemas
* cómo manejar eventos duplicados

Quiero implementar el patrón personalmente.

---

# 14. DATABASE

Configura PostgreSQL.

Prepara:

```text
src/main/resources/db/migration/
```

Utiliza una herramienta de migraciones apropiada, preferiblemente Flyway.

Crea únicamente una migración inicial si es necesaria para arrancar.

NO generes automáticamente toda la base de datos si eso impide que yo practique el diseño.

Quiero aprender:

* PK
* FK
* índices
* constraints
* relaciones
* optimistic locking
* transacciones

Añade:

```text
docs/DATABASE_DESIGN.md
```

con las decisiones que debería estudiar.

---

# 15. DOCKER

Crea:

```text
Dockerfile
docker-compose.yml
.dockerignore
```

Docker Compose debe permitir levantar:

```text
Spring Boot
PostgreSQL
Redis
RabbitMQ
```

No necesito todavía Kubernetes.

Pero crea:

```text
docs/DOCKER.md
```

con una guía de cosas que debo aprender:

* imágenes
* containers
* volumes
* networks
* environment variables
* health checks
* multi-stage builds

---

# 16. TESTING

Prepara:

```text
unit tests
integration tests
```

y configuración inicial para:

```text
JUnit 5
Mockito
Testcontainers
```

NO escribas todos los tests.

Crea algunos ejemplos mínimos y TODOs.

Quiero implementar yo:

* unit tests
* integration tests
* repository tests
* controller tests
* security tests
* RabbitMQ integration tests

---

# 17. EXCEPTION HANDLING

Crea la estructura para un manejo global de excepciones.

Por ejemplo:

```text
GlobalExceptionHandler
ErrorResponse
```

Prepara excepciones de dominio como:

```text
ResourceNotFoundException
UnauthorizedException
ForbiddenException
BusinessException
```

Pero no implementes toda la lógica.

Quiero aprender a decidir qué HTTP status corresponde a cada error.

---

# 18. OBSERVABILITY

Añade Spring Boot Actuator.

Prepara la estructura para posteriormente añadir:

```text
health checks
metrics
structured logging
correlation ID
```

No añadas Prometheus/Grafana todavía.

---

# 19. CI/CD

Crea:

```text
.github/
└── workflows/
    └── ci.yml
```

El pipeline debe tener inicialmente:

```text
Checkout
 ↓
Setup Java 21
 ↓
Maven build
 ↓
Unit tests
 ↓
Integration tests
```

NO implementes todavía el deployment AWS.

Lo añadiremos en una fase posterior.

---

# 20. DOCUMENTACIÓN DE APRENDIZAJE

Esta parte es MUY IMPORTANTE.

Crea una carpeta:

```text
docs/
```

con:

```text
ARCHITECTURE.md
REST_API.md
SECURITY.md
DATABASE_DESIGN.md
EVENT_DRIVEN.md
RABBITMQ.md
REDIS.md
OUTBOX_PATTERN.md
TESTING.md
DOCKER.md
AWS.md
INTERVIEW_NOTES.md
```

Cada documento debe contener:

1. Qué concepto debo aprender.
2. Por qué se utiliza.
3. Qué problema soluciona.
4. Qué debo implementar yo.
5. Preguntas típicas de entrevista relacionadas.
6. Un checklist de progreso.

NO quiero que escribas la solución completa.

Quiero que sean **guías de aprendizaje**.

---

# 21. README PRINCIPAL

Crea un README.md profesional.

Debe contener:

## Project overview

Explicación breve.

## Architecture

Diagrama Mermaid.

## Tech stack

Lista de tecnologías.

## Features

Checklist:

```text
[ ] Authentication
[ ] Authorization
[ ] JWT
[ ] Refresh tokens
[ ] Token revocation
[ ] Redis
[ ] REST API
[ ] PostgreSQL
[ ] RabbitMQ
[ ] Event-driven architecture
[ ] Idempotency
[ ] Outbox Pattern
[ ] Testing
[ ] Docker
[ ] CI/CD
[ ] AWS
```

## Getting started

Explica cómo arrancar el proyecto.

## Learning roadmap

Indica en qué orden debería implementar las funcionalidades.

---

# 22. REGLA MÁS IMPORTANTE

NO IMPLEMENTES LA APLICACIÓN COMPLETA.

Quiero que el repositorio sea un **esqueleto educativo**.

Cuando exista una parte que requiera conocimiento importante, crea:

```java
// TODO: Implement this yourself.
// Learning objective: ...
```

y explica qué debería estudiar antes de implementarlo.

No generes automáticamente:

* lógica de negocio
* JWT completo
* blacklist completa
* RabbitMQ completo
* Outbox completo
* caching completo
* tests completos
* AWS deployment completo

Esas partes quiero programarlas yo.

---

# 23. PLAN DE IMPLEMENTACIÓN

Finalmente crea:

```text
docs/IMPLEMENTATION_PLAN.md
```

con un roadmap progresivo.

Debe estar dividido en fases:

### Fase 1

Project setup

### Fase 2

Domain

### Fase 3

REST API

### Fase 4

PostgreSQL

### Fase 5

Authentication

### Fase 6

Authorization

### Fase 7

JWT + refresh tokens

### Fase 8

Redis + token revocation

### Fase 9

Orders

### Fase 10

RabbitMQ

### Fase 11

Event-driven architecture

### Fase 12

Idempotency

### Fase 13

Transactional Outbox

### Fase 14

Testing + Testcontainers

### Fase 15

Docker

### Fase 16

CI/CD

### Fase 17

AWS

Para cada fase indica:

* qué debo estudiar
* qué debo implementar
* qué conceptos estoy aprendiendo
* qué preguntas de entrevista debería poder responder
* criterios para considerar la fase terminada

---

# 24. RESULTADO FINAL

Cuando termines tu trabajo quiero tener:

```text
order-management-api/
│
├── src/
├── docs/
├── .github/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── .gitignore
└── .dockerignore
```

El proyecto debe:

* compilar
* arrancar
* tener una estructura limpia
* tener configuración básica
* contener TODOs educativos
* NO contener la implementación completa de las funcionalidades

Antes de crear archivos, analiza la arquitectura completa y asegúrate de que las dependencias entre módulos sean coherentes.

Si detectas una decisión arquitectónica que pueda ser discutible, documenta el trade-off en `docs/ARCHITECTURE.md`.

Tu objetivo no es escribir el proyecto por mí.

Tu objetivo es **preparar un entorno profesional en el que yo tenga que escribir la parte difícil y aprender mientras lo hago**.