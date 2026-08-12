package com.springcommerce.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * CreateOrderResponse DTO
 * 
 * Data Transfer Object for the response after creating an order.
 * This is what the REST API returns to the client.
 * 
 * TODO: Define response structure in PHASE 3 (REST API)
 * Learning objective: Understand API response design
 * Questions to answer:
 * - What information should be returned to the client?
 * - Should we return the entire Order entity or a subset?
 * - How do we handle sensitive information?
 * - Should the response include links (HATEOAS)?
 */
@Data
@AllArgsConstructor
@Builder
public class CreateOrderResponse {
    
    private UUID id;
    private String orderNumber;
    private LocalDateTime createdAt;
    
    // TODO: Add response fields in PHASE 3
    // private String status;
    // private BigDecimal totalAmount;
    // private LocalDateTime updatedAt;
}
