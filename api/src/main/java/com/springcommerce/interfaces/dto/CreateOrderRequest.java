package com.springcommerce.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * CreateOrderRequest DTO
 * 
 * Data Transfer Object for creating an order via REST API.
 * DTOs belong to the INTERFACES layer and are specific to HTTP communication.
 * 
 * They serve as a contract between the REST API and clients.
 * They are NOT the same as domain entities.
 * 
 * TODO: Add validation rules in PHASE 3 (REST API)
 * Learning objective: Understand DTOs, API contracts, and validation
 * Questions to answer:
 * - Why are DTOs different from domain entities?
 * - What validation rules should apply to API inputs?
 * - How do we map DTOs to domain entities?
 * - Should DTOs expose all domain information?
 */
@Data
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    
    // TODO: Define order creation fields in PHASE 3
    // @NotNull
    // @NotBlank
    // private String customerId;
    
    // @NotEmpty
    // private List<OrderItemRequest> items;
    
    // @NotNull
    // private ShippingAddressRequest shippingAddress;
    
    // @NotNull
    // private BillingAddressRequest billingAddress;
    
    // @NotNull
    // @Positive
    // private BigDecimal totalAmount;
    
    // TODO: Add nested DTOs:
    // - OrderItemRequest
    // - ShippingAddressRequest
    // - BillingAddressRequest
}
