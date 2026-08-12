# Spring Commerce - Order Management & Processing API

An educational backend project to learn modern Backend Engineering concepts through incremental implementation.

## 🎯 Project Purpose

This is a **learning project**, not a production application. The goal is to implement a real Order Management & Processing API while mastering:

- **Clean/Hexagonal Architecture**
- **Domain-Driven Design (DDD)**
- **Spring Boot best practices**
- **Database design with PostgreSQL**
- **Authentication & Security (JWT, OAuth)**
- **Redis for caching and token management**
- **Event-driven architecture with RabbitMQ**
- **Testing strategies (Unit, Integration, E2E)**
- **Docker & CI/CD pipelines**
- **AWS deployment**

## 🛠 Tech Stack

| Technology | Purpose |
|-----------|---------|
| **Java 21** | Language |
| **Spring Boot 3.3.x** | Framework |
| **Maven** | Build tool |
| **PostgreSQL 15** | Primary database |
| **Redis 7** | Cache & token revocation |
| **RabbitMQ 3.12** | Event messaging |
| **JUnit 5** | Testing framework |
| **Mockito** | Mocking |
| **Testcontainers** | Integration testing |
| **Docker** | Containerization |
| **GitHub Actions** | CI/CD |
| **AWS** | Cloud deployment |

## 📋 Development Phases

The project is divided into **17 learning phases**. See [DEVELOPMENT_PHASES.md](docs/DEVELOPMENT_PHASES.md) for details.

| Phase | Topic | Status |
|-------|-------|--------|
| 1 | Project Setup | ✅ In Progress |
| 2 | Domain Layer | ⏳ TODO |
| 3 | REST API | ⏳ TODO |
| 4 | PostgreSQL Persistence | ⏳ TODO |
| 5-17 | ... | ⏳ TODO |

## 🚀 Quick Start

### Prerequisites

- **Java 21+** — [Install](https://adoptium.net/)
- **Maven 3.9+** — [Install](https://maven.apache.org/install.html)
- **Docker & Docker Compose** — [Install](https://www.docker.com/products/docker-desktop)
- **Git**

### Clone & Setup

```bash
# Clone repository
git clone https://github.com/yourusername/spring-commerce.git
cd spring-commerce

# Install dependencies
cd api
mvn clean install

# Start infrastructure (PostgreSQL, Redis, RabbitMQ)
cd ..
docker-compose up -d

# Run the application
cd api
mvn spring-boot:run

# Check if it's running
curl http://localhost:8080/api/orders
```

### IDE Setup (IntelliJ IDEA / VS Code)

1. **Open Project**: File → Open → Select `spring-commerce` folder
2. **Configure JDK**: Project Settings → Java Compiler → Set to Java 21
3. **Maven Reload**: Right-click `pom.xml` → Maven → Reload Project
4. **Verify**: Run `OrderControllerTest` — should pass

## 📚 Documentation

- [**ARCHITECTURE.md**](docs/ARCHITECTURE.md) — Clean/Hexagonal Architecture explanation
- [**DEVELOPMENT_PHASES.md**](docs/DEVELOPMENT_PHASES.md) — Detailed phase breakdown
- [**GETTING_STARTED.md**](docs/GETTING_STARTED.md) — Troubleshooting & setup guide
- [**REST_API.md**](docs/REST_API.md) — API contract (PHASE 3)
- [**DATABASE_DESIGN.md**](docs/DATABASE_DESIGN.md) — Schema & relationships (PHASE 4)
- [**SECURITY.md**](docs/SECURITY.md) — Authentication/Authorization (PHASES 5-8)
- [**EVENT_DRIVEN.md**](docs/EVENT_DRIVEN.md) — RabbitMQ & events (PHASES 10-13)
- [**TESTING.md**](docs/TESTING.md) — Test strategies (PHASE 14)
- [**DOCKER.md**](docs/DOCKER.md) — Containerization (PHASE 15)
- [**CI_CD.md**](docs/CI_CD.md) — GitHub Actions (PHASE 16)
- [**AWS.md**](docs/AWS.md) — Deployment to AWS (PHASE 17)

## 🏛️ Architecture Overview

```
┌─────────────────────────────────────────┐
│     INTERFACES (HTTP, REST)             │
│     Controllers, DTOs, Mappers          │
├─────────────────────────────────────────┤
│     APPLICATION (Use Cases)             │
│     Services, Business Logic Orchestration
├─────────────────────────────────────────┤
│     DOMAIN (Core Business Logic)        │
│     Entities, Value Objects, Ports      │
├─────────────────────────────────────────┤
│     INFRASTRUCTURE (Adapters)           │
│     Repositories, Messaging, External   │
└─────────────────────────────────────────┘
```

**Key Principle**: Dependencies flow INWARD. Domain has NO external dependencies.

## 📖 How to Use This Project

### ✅ Respect PHASE 1 Only

**PHASE 1 is now complete**. The project structure is ready. Do NOT implement logic from future phases yet.

### 📝 For Each Phase

1. **Read** the phase documentation
2. **Understand** the learning objectives
3. **Implement** the features yourself
4. **Review** your implementation against best practices
5. **Test** thoroughly
6. **Move** to next phase only when ready

### 🤔 You'll Find TODOs Like This

```java
// TODO: Implement this yourself.
// Learning objective: Understand domain-driven design
// Questions to answer:
// - What are the essential attributes?
// - What invariants must be maintained?
```

**Don't skip these**. The TODOs guide your learning.

### 💡 Educational Guidelines

- **Prefer Learning Over Speed**: Take time to understand concepts
- **Ask Questions**: Why? How? When? What if?
- **Test Thoroughly**: Write tests BEFORE implementation (TDD)
- **Read Code**: Study frameworks you use (Spring, Hibernate, etc.)
- **Document Decisions**: Explain architectural choices

## 🔄 Development Workflow

```bash
# 1. Start the environment
docker-compose up -d

# 2. Run the app
cd api && mvn spring-boot:run

# 3. In another terminal, run tests
mvn test

# 4. Make changes, tests auto-refresh

# 5. Stop everything
docker-compose down
```

## 🐛 Troubleshooting

See [GETTING_STARTED.md](docs/GETTING_STARTED.md) for common issues:

- **Java version mismatch** → Set JAVA_HOME to Java 21
- **Maven not found** → Add to PATH or use wrapper
- **Docker connection issues** → Check Docker daemon is running
- **Port conflicts** → Change ports in `docker-compose.yml`

## 🧪 Testing

```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=OrderControllerTest

# Run with coverage
mvn clean test jacoco:report

# View coverage: target/site/jacoco/index.html
```

## 🐳 Docker

```bash
# Build image
docker build -t spring-commerce-api .

# Run container
docker run -p 8080:8080 spring-commerce-api

# See logs
docker logs -f container_id
```

## 🔗 Useful Links

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)
- [Redis Docs](https://redis.io/documentation)
- [RabbitMQ Docs](https://www.rabbitmq.com/documentation.html)

## 📝 Code Quality Standards

- Follow **SOLID** principles
- Keep code **DRY** (Don't Repeat Yourself)
- Write **clean code** that others can understand
- **Test everything** (Unit + Integration + E2E)
- **Document decisions** (Why? Not just What?)
- Use **meaningful names** for variables, methods, classes
- Avoid **premature optimization**

## 🎓 Interview Preparation

This project prepares you to:

- ✅ Explain architectural decisions in detail
- ✅ Defend design choices against alternatives
- ✅ Discuss trade-offs (consistency vs. availability, etc.)
- ✅ Implement features from scratch in interviews
- ✅ Code review and provide feedback
- ✅ Lead technical discussions

## 📄 License

Educational use only. No production warranty.

## 🤝 Contributing

This is a personal learning project. Suggestions welcome!

---

**Ready to start?** 👉 Read [GETTING_STARTED.md](docs/GETTING_STARTED.md)
