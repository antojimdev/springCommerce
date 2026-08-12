package com.springcommerce.interfaces.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springcommerce.interfaces.dto.CreateOrderRequest;
import com.springcommerce.interfaces.dto.CreateOrderResponse;

/**
 * OrderController - REST API Endpoints
 * 
 * Controllers belong to the INTERFACES layer.
 * They are the entry points for HTTP requests.
 * 
 * Important rules:
 * 1. NO business logic here (belongs in application layer)
 * 2. NO direct database access (use repositories through application layer)
 * 3. Controllers should be thin - mostly routing and marshalling
 * 4. Validate input, call use cases, return responses
 * 
 * TODO: Implement endpoints in PHASE 3 (REST API)
 * Learning objective: Understanding controller responsibilities and REST conventions
 * Questions to answer:
 * - What HTTP methods and status codes should we use?
 * - How do we handle errors in the controller?
 * - Should we use @RequestBody or query params?
 * - How do we map DTOs to domain entities?
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    // TODO: Inject CreateOrderUseCase in PHASE 3
    
    /**
     * POST /api/orders - Create a new order
     * 
     * TODO: Implement in PHASE 3
     * - Validate CreateOrderRequest
     * - Call CreateOrderUseCase
     * - Return 201 Created with CreateOrderResponse
     * - Handle exceptions (400, 422, 500)
     */
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        // TODO: Implement endpoint
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    
    /**
     * GET /api/orders/{id} - Get order by ID
     * 
     * TODO: Implement in PHASE 3
     * - Extract ID from path variable
     * - Call GetOrderByIdUseCase
     * - Return 200 OK with OrderResponse
     * - Return 404 NOT_FOUND if order doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        // TODO: Implement endpoint
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    
    /**
     * GET /api/orders - Get all orders
     * 
     * TODO: Implement in PHASE 3
     * - Support pagination (page, size)
     * - Call ListOrdersUseCase
     * - Return 200 OK with OrderResponse list
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        // TODO: Implement endpoint
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
