# Getting Started Guide

This document helps you set up and troubleshoot the Spring Commerce project.

## Prerequisites

### Java Development Kit (JDK) 21

**Check if installed**:
```bash
java -version
javac -version
```

**Expected output**:
```
openjdk version "21.0.x" 2023-XX-XX
```

**Install**:
- **macOS**: `brew install openjdk@21`
- **Ubuntu/Debian**: `sudo apt-get install openjdk-21-jdk`
- **Windows**: [Download from Adoptium](https://adoptium.net/)

**Set JAVA_HOME** (if not auto-detected):
```bash
# Add to ~/.bashrc or ~/.zshrc
export JAVA_HOME=$(which java | xargs readlink -f | xargs dirname | xargs dirname)
```

### Maven 3.9+

**Check if installed**:
```bash
mvn --version
```

**Expected output**:
```
Maven 3.9.x
Java version: 21.0.x
```

**Install**:
- **macOS**: `brew install maven`
- **Ubuntu/Debian**: `sudo apt-get install maven`
- **Windows**: [Download from Maven](https://maven.apache.org/download.cgi)

### Docker & Docker Compose

**Check if installed**:
```bash
docker --version
docker-compose --version
```

**Install**:
- [Docker Desktop](https://www.docker.com/products/docker-desktop) (includes Docker Compose on macOS/Windows)
- **Linux**: Follow [Docker docs](https://docs.docker.com/engine/install/)

## Initial Setup

### 1. Clone Repository

```bash
git clone https://github.com/yourusername/spring-commerce.git
cd spring-commerce
```

### 2. Verify Java Setup

```bash
java -version
# Should show Java 21

mvn --version
# Should show Maven 3.9+
```

### 3. Install Dependencies

```bash
cd api
mvn clean install
# Downloads all Maven dependencies (~5-10 minutes first time)
```

**Expected output**:
```
[INFO] BUILD SUCCESS
```

### 4. Start Infrastructure

```bash
cd ..  # Back to project root
docker-compose up -d
```

**Verify services are running**:
```bash
docker-compose ps
```

**Expected output**:
```
NAME                          STATUS
spring_commerce_postgres      Up (healthy)
spring_commerce_redis         Up (healthy)
spring_commerce_rabbitmq      Up (healthy)
spring_commerce_app           Up
```

### 5. Run Application

```bash
cd api
mvn spring-boot:run
```

**Expected output**:
```
2024-XX-XX XX:XX:XX.XXX  INFO (...) Started SpringCommerceApplication in X.XXX seconds
```

### 6. Verify It's Running

In another terminal:
```bash
curl http://localhost:8080/api/orders
# Should return 200 or 501 (Not Implemented)
```

### 7. Run Tests

```bash
cd api
mvn clean test
```

**Expected output**:
```
[INFO] Tests run: X, Failures: 0, Errors: 0, Skipped: 0
```

---

## IDE Setup

### IntelliJ IDEA (Recommended)

1. **Open Project**:
   - File → Open → Select `spring-commerce` folder
   - Trust project when prompted

2. **Configure JDK**:
   - File → Project Structure → SDK
   - Set to Java 21
   - Click Apply

3. **Configure Maven**:
   - File → Settings → Build Tools → Maven
   - Maven home path: Auto or `/usr/local/Cellar/maven/...`
   - Runner: Set VM options if needed

4. **Reload Maven**:
   - Right-click `api/pom.xml` → Maven → Reload Project
   - Wait for indexing to complete (~1-2 minutes)

5. **Mark Directories**:
   - Right-click `api/src/main/java` → Mark Directory as → Sources Root
   - Right-click `api/src/test/java` → Mark Directory as → Test Sources Root

6. **Run Tests**:
   - Right-click test class → Run
   - Or use Ctrl+Shift+F10 (Windows/Linux) or Cmd+Shift+R (macOS)

### VS Code

1. **Install Extensions**:
   - Extension Pack for Java (Microsoft)
   - Spring Boot Extension Pack (Pivotal)
   - Docker (Microsoft)

2. **Open Folder**:
   - File → Open Folder → Select `spring-commerce`

3. **Configure JDK**:
   - Cmd+Shift+P (macOS) / Ctrl+Shift+P (Windows/Linux)
   - Type "Java: Configure Runtime"
   - Select Java 21

4. **Maven in VS Code**:
   - Open terminal
   - Run: `cd api && mvn clean install`

5. **Run/Debug**:
   - Terminal → Run Build Task → Select Maven task
   - Click "Debug" icon on test class

---

## Common Issues & Troubleshooting

### Issue: `Java version is not 21`

**Error**:
```
[ERROR] COMPILATION ERROR :
[INFO] The source value does not support target value 21
```

**Solution**:
```bash
# Check JAVA_HOME
echo $JAVA_HOME

# Should point to Java 21. If not, set it:
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
# macOS

export JAVA_HOME=$(which java | xargs readlink -f | xargs dirname | xargs dirname)
# Linux

# Verify
java -version
```

### Issue: `Maven not found`

**Error**:
```
mvn: command not found
```

**Solution**:
```bash
# Install Maven
brew install maven  # macOS
# or
sudo apt-get install maven  # Ubuntu

# Verify
mvn --version
```

### Issue: `Docker daemon not running`

**Error**:
```
Cannot connect to Docker daemon
```

**Solution**:
- Start Docker Desktop (macOS/Windows)
- Or: `sudo systemctl start docker` (Linux)

### Issue: `Port 5432 already in use`

**Error**:
```
ERROR: for spring_commerce_postgres  Cannot start service postgres: 
Ports are not available: exposing port TCP 0.0.0.0:5432 -> 0.0.0.0:5432: 
address already in use
```

**Solution (Option 1 - Stop other services)**:
```bash
# Find what's using port 5432
lsof -i :5432  # macOS/Linux
netstat -ano | findstr :5432  # Windows

# Kill it or use different port in docker-compose.yml
```

**Solution (Option 2 - Change ports)**:
```bash
# Edit docker-compose.yml
# Change:
#   ports:
#     - "5432:5432"
# To:
#     - "5433:5432"

# Then restart:
docker-compose down
docker-compose up -d
```

### Issue: `BUILD FAILURE - Missing dependencies`

**Error**:
```
[ERROR] Failed to execute goal on project spring-commerce-api: 
Could not resolve dependencies for project
```

**Solution**:
```bash
# Clean and retry
mvn clean install

# Or clear Maven cache
rm -rf ~/.m2/repository
mvn clean install

# Check internet connection
ping maven.apache.org
```

### Issue: `Tests fail with database connection error`

**Error**:
```
org.postgresql.util.PSQLException: Connection to localhost:5432 refused
```

**Solution**:
```bash
# Start Docker services
docker-compose up -d

# Verify they're healthy
docker-compose ps

# Check logs
docker-compose logs postgres
```

### Issue: `Spring Boot application won't start`

**Error**:
```
Failed to bind property 'spring.datasource.url' to java.lang.String
```

**Solution**:
```bash
# Make sure Docker services are running
docker-compose ps

# Check application.yml syntax
cd api/src/main/resources
cat application.yml  # Check for YAML errors

# Run with debug logging
cd ..
mvn spring-boot:run -e -X
```

---

## Development Workflow

### Daily Development

```bash
# 1. Start infrastructure (once per day)
docker-compose up -d

# 2. In one terminal: Run the app
cd api
mvn spring-boot:run

# 3. In another terminal: Run tests
mvn test

# 4. In your IDE: Write code, tests auto-refresh

# 5. When done: Stop the app (Ctrl+C) and optionally stop Docker
docker-compose stop
```

### Running Specific Tests

```bash
# Run all tests in a class
mvn test -Dtest=OrderControllerTest

# Run specific test method
mvn test -Dtest=OrderControllerTest#testCreateOrderSuccess

# Run with pattern matching
mvn test -Dtest=*OrderTest

# Run with logging
mvn test -e -X
```

### Building JAR

```bash
# Create executable JAR
mvn clean package

# JAR location: api/target/spring-commerce-api-1.0.0.jar

# Run JAR
java -jar api/target/spring-commerce-api-1.0.0.jar

# Override properties
java -Dspring.profiles.active=prod -jar api/target/spring-commerce-api-1.0.0.jar
```

### Docker Commands

```bash
# View all services and their status
docker-compose ps

# View logs
docker-compose logs           # All
docker-compose logs -f app    # Follow app logs only
docker-compose logs postgres  # Postgres logs

# Stop services
docker-compose stop           # Graceful stop
docker-compose down           # Stop and remove containers
docker-compose down -v        # Stop, remove containers AND volumes

# Restart
docker-compose restart

# Remove and recreate
docker-compose down -v
docker-compose up -d

# Access database CLI
docker exec -it spring_commerce_postgres psql -U spring_user -d spring_commerce

# Access Redis CLI
docker exec -it spring_commerce_redis redis-cli

# Access RabbitMQ Management UI
# Open: http://localhost:15672
# Username: guest
# Password: guest
```

---

## IDE Keyboard Shortcuts

### IntelliJ IDEA (macOS)

| Action | Shortcut |
|--------|----------|
| Run test | Cmd+Shift+R |
| Debug test | Cmd+Shift+D |
| Go to class | Cmd+O |
| Go to file | Cmd+Shift+O |
| Find in files | Cmd+Shift+F |
| Refactor rename | Ctrl+T |
| Quick fix | Cmd+1 |
| Format code | Cmd+Option+L |
| Organize imports | Ctrl+Option+O |
| Show test coverage | Cmd+Shift+C |

### IntelliJ IDEA (Windows/Linux)

| Action | Shortcut |
|--------|----------|
| Run test | Ctrl+Shift+F10 |
| Debug test | Ctrl+Shift+F9 |
| Go to class | Ctrl+N |
| Go to file | Ctrl+Shift+N |
| Find in files | Ctrl+Shift+F |
| Refactor rename | Ctrl+Shift+Alt+R |
| Quick fix | Alt+Enter |
| Format code | Ctrl+Alt+L |
| Organize imports | Ctrl+Alt+O |

---

## Performance Tips

### Faster Maven Builds

```bash
# Skip tests
mvn clean install -DskipTests

# Parallel execution
mvn clean install -T 1C  # 1 thread per core

# Offline mode (after first build)
mvn clean install -o

# Reuse downloaded artifacts
mvn clean install -e  # Error details
```

### Faster IDE Startup

- Increase heap: Help → Edit Custom Properties → `-Xmx2g`
- Disable unnecessary plugins
- Enable "Power Save Mode" (Settings → Power Save Mode)

---

## Useful Commands Summary

```bash
# Setup (one-time)
git clone https://github.com/yourusername/spring-commerce.git
cd spring-commerce && cd api
mvn clean install

# Daily development
cd ..
docker-compose up -d

cd api
mvn spring-boot:run  # Terminal 1
mvn test              # Terminal 2

# Check logs
docker-compose logs -f app
docker-compose logs postgres

# Stop everything
docker-compose down

# Clean everything
docker-compose down -v
mvn clean
```

---

## Next Steps

1. ✅ Verify all services are running
2. ✅ Confirm tests pass: `mvn test`
3. ✅ Read [ARCHITECTURE.md](ARCHITECTURE.md) to understand the project structure
4. ✅ Read [DEVELOPMENT_PHASES.md](DEVELOPMENT_PHASES.md) to see what's coming
5. 👉 Start Phase 2: Domain Layer implementation

---

## Getting Help

### When Stuck

1. **Check logs**:
   ```bash
   docker-compose logs app
   mvn spring-boot:run -e -X
   ```

2. **Search errors**:
   - Copy error message → Google
   - Check Stack Overflow
   - Check Spring Boot docs

3. **Read documentation**:
   - [Spring Boot Guide](https://spring.io/guides)
   - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
   - [PostgreSQL Docs](https://www.postgresql.org/docs/)

4. **Consult architecture docs**:
   - [ARCHITECTURE.md](ARCHITECTURE.md)
   - Relevant phase documentation
   - Inline code TODOs and comments

---

**You're all set!** 🎉 Start with Phase 2: Domain Layer.
