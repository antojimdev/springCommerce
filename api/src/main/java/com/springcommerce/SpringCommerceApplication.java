package com.springcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Commerce API - Educational Backend Project
 * 
 * This is an Order Management & Processing API built with:
 * - Java 21
 * - Spring Boot 3.x
 * - PostgreSQL
 * - Clean/Hexagonal Architecture
 * 
 * Development phases:
 * PHASE 1: Project Setup (current)
 * PHASE 2-17: See docs/DEVELOPMENT_PHASES.md
 * 
 * Architecture: Domain → Application → Interfaces → Infrastructure
 * The domain layer has NO Spring dependencies.
 */
@SpringBootApplication
public class SpringCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCommerceApplication.class, args);
    }
}
