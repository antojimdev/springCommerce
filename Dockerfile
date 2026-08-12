# Multi-stage Dockerfile for Spring Commerce API
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy Maven POM
COPY api/pom.xml .

# Download dependencies (cache layer)
RUN mvn dependency:go-offline -B

# Copy source code
COPY api/src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy built JAR from builder stage
COPY --from=builder /app/target/spring-commerce-api-1.0.0.jar app.jar

# Create non-root user for security
RUN addgroup -g 1000 spring && \
    adduser -D -u 1000 -G spring spring && \
    chown -R spring:spring /app

USER spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/api/orders || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

# TODO: Add performance tuning in production deployment
# - JVM memory settings: -Xmx1g -Xms512m
# - GC tuning: -XX:+UseG1GC
# - Monitoring: -XX:+UnlockDiagnosticVMOptions
