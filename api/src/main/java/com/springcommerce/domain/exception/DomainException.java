package com.springcommerce.domain.exception;

/**
 * Base Domain Exception
 * 
 * All domain-specific exceptions should extend this class.
 * This is part of the DOMAIN layer and has no Spring dependencies.
 * 
 * TODO: Add domain-specific exceptions in PHASE 2
 * - OrderNotFoundException
 * - InvalidOrderStatusException
 * - OrderAlreadyProcessedException
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
